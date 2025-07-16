package io.github.freitas022.packlet.order.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import io.github.freitas022.packlet.order.client.CatalogClient;
import io.github.freitas022.packlet.order.client.UserClient;
import io.github.freitas022.packlet.order.dto.OrderCreatedEvent;
import io.github.freitas022.packlet.order.dto.OrderRequestDTO;
import io.github.freitas022.packlet.order.dto.UserDTO;
import io.github.freitas022.packlet.order.model.Customer;
import io.github.freitas022.packlet.order.model.Item;
import io.github.freitas022.packlet.order.model.Order;
import io.github.freitas022.packlet.order.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CatalogClient catalogClient;

    @Autowired
    UserClient userClient;

    @Autowired
    SqsTemplate sqsTemplate;

    ObjectMapper objectMapper = new ObjectMapper();

    @Value("${app.queues.orderCreated}")
    private String queueName;

    public Mono<Order> findById(String id) {
        return orderRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado")));
    }

    public Mono<Order> save(OrderRequestDTO dto) {
        Mono<UserDTO> userMono = userClient.fetchUserById(dto.customerId());
        Mono<List<Item>> itemsMono = enrichItems(dto.items());

        return Mono.zip(userMono, itemsMono)
                .map(tuple -> {
                    UserDTO user = tuple.getT1();
                    List<Item> items = tuple.getT2();
                    return Order.builder()
                            .moment(LocalDateTime.now())
                            .customer(new Customer(user.getId(), user.getName()))
                            .items(items)
                            .updatedAt(LocalDateTime.now())
                            .build();
                })
                .flatMap(orderRepository::save)
                .doOnSuccess(order -> {
                    // Publica o evento "pedido criado"
                    try {
                        OrderCreatedEvent event = new ModelMapper().map(order, OrderCreatedEvent.class);
                        String payload = objectMapper.writeValueAsString(event);
                        sqsTemplate.sendAsync(queueName, payload);
                    } catch (Exception e) {
                        log.error("Erro ao serializar o pedido com ID: {}", order.getId());
                    }
                });
    }

    private Mono<List<Item>> enrichItems(List<Item> items) {
        return Flux.fromIterable(items)
                .flatMap(this::enrichProductData)
                .collectList();
    }

    private Mono<Item> enrichProductData(Item item) {
        return catalogClient.fetchProductById(item.getProductId())
                .map(product -> new Item(
                        product.getId(),
                        product.getName(),
                        item.getQuantity(),
                        product.getPrice()
                ));
    }
}
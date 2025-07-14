package io.github.freitas022.packlet.order.service;

import io.github.freitas022.packlet.order.client.CatalogClient;
import io.github.freitas022.packlet.order.client.UserClient;
import io.github.freitas022.packlet.order.dto.OrderRequestDTO;
import io.github.freitas022.packlet.order.dto.UserDTO;
import io.github.freitas022.packlet.order.model.Customer;
import io.github.freitas022.packlet.order.model.Item;
import io.github.freitas022.packlet.order.model.Order;
import io.github.freitas022.packlet.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CatalogClient catalogClient;

    @Autowired
    UserClient userClient;

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
                .flatMap(orderRepository::save);
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
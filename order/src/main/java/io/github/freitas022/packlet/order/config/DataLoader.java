package io.github.freitas022.packlet.order.config;

import io.github.freitas022.packlet.order.model.Customer;
import io.github.freitas022.packlet.order.model.Item;
import io.github.freitas022.packlet.order.model.Order;
import io.github.freitas022.packlet.order.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
@Slf4j
@Profile("test")
public class DataLoader {

    @Bean
    public CommandLineRunner commandLineRunner(OrderRepository orderRepository) {
        return args -> {
            log.info(">>>> INICIANDO DATA LOADER PARA PEDIDOS...");

            var order1 = Order.builder()
                    .moment(LocalDateTime.now())
                    .customer(new Customer(1L, "Cliente 1"))
                    .items(List.of(
                            new Item(null, "Teste 1", 2, BigDecimal.TEN),
                            new Item(null, "Teste 2", 1, BigDecimal.ONE)
                    ))
                    .updatedAt(LocalDateTime.now())
                    .build();

            var order2 = Order.builder()
                    .moment(LocalDateTime.now())
                    .customer(new Customer(2L, "Cliente 2"))
                    .items(List.of(
                            new Item(null, "60d21b4667d0d8992e610c87", 5, BigDecimal.TEN)
                    ))
                    .updatedAt(LocalDateTime.now())
                    .build();

            orderRepository.deleteAll()
                    .thenMany(
                            Flux.just(order1, order2)
                                    .flatMap(orderRepository::save)
                    )
                    .subscribe(
                            order -> log.info("Pedido salvo com ID: {}", order.getId()),
                            error -> log.error("Erro ao salvar pedido no DataLoader: ", error),
                            () -> log.info(">>>> DATA LOADER FINALIZADO.")
                    );
        };
    }
}

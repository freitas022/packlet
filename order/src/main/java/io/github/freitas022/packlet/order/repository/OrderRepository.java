package io.github.freitas022.packlet.order.repository;

import io.github.freitas022.packlet.order.model.Order;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;


public interface OrderRepository extends ReactiveMongoRepository<Order, String> {
}

package io.github.freitas022.packlet.order.controller;

import io.github.freitas022.packlet.order.dto.OrderRequestDTO;
import io.github.freitas022.packlet.order.model.Order;
import io.github.freitas022.packlet.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Order> findById(@PathVariable String id) {
        return orderService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Order> save(@RequestBody OrderRequestDTO dto) {
        return orderService.save(dto);
    }
}

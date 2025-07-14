package io.github.freitas022.packlet.order.dto;

import io.github.freitas022.packlet.order.model.Item;

import java.util.List;

public record OrderRequestDTO(

        Long customerId,
        List<Item> items
) {
}

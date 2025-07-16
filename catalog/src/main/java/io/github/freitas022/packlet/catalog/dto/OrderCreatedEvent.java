package io.github.freitas022.packlet.catalog.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderCreatedEvent {

    private String id;
    private List<ItemEventDTO> items;

    @Data
    public static class ItemEventDTO {
        private Long productId;
        private Integer quantity;
    }
}

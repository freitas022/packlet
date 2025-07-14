package io.github.freitas022.packlet.order.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemDTO {

    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal price;
}

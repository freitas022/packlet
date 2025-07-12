package io.github.freitas022.packlet.catalog.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductMinDTO {

    private Long id;
    private String name;
    private String shortDescription;
    private BigDecimal price;
    private String imgUrl;
}

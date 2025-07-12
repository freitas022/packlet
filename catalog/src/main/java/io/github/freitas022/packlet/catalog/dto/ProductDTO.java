package io.github.freitas022.packlet.catalog.dto;

import io.github.freitas022.packlet.catalog.model.Category;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class ProductDTO {

    private Long id;
    private String name;
    private String longDescription;
    private String shortDescription;
    private String imgUrl;
    private BigDecimal price;
    private String sku;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<Category> categories;
}

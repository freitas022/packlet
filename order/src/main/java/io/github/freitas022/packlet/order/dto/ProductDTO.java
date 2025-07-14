package io.github.freitas022.packlet.order.dto;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

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
    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal price;
    private String sku;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<CategoryDTO> categories;
}

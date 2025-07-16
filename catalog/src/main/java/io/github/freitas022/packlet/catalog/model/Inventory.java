package io.github.freitas022.packlet.catalog.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity(name = "TB_INVENTORY")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @NotNull
    @Min(0)
    private Integer quantity;
    @Min(0)
    private Integer reservedStock;
    @Min(0)
    private Integer minimumStock;

    @CreationTimestamp
    private LocalDateTime lastInboundAt;

    @UpdateTimestamp
    private LocalDateTime lastOutboundAt;
}

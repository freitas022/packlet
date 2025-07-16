package io.github.freitas022.packlet.catalog.repository;

import io.github.freitas022.packlet.catalog.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Inventory findByProductId(Long productId);
}

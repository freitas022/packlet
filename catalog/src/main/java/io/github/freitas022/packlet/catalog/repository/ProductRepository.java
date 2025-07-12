package io.github.freitas022.packlet.catalog.repository;

import io.github.freitas022.packlet.catalog.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContainingIgnoreCase(String name);
}

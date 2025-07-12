package io.github.freitas022.packlet.catalog.repository;

import io.github.freitas022.packlet.catalog.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}

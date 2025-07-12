package io.github.freitas022.packlet.catalog.service;

import io.github.freitas022.packlet.catalog.model.Category;
import io.github.freitas022.packlet.catalog.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}

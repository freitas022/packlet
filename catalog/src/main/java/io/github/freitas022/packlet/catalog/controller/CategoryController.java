package io.github.freitas022.packlet.catalog.controller;

import io.github.freitas022.packlet.catalog.model.Category;
import io.github.freitas022.packlet.catalog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Category> findAll() {
        return categoryService.findAll();
    }
}

package io.github.freitas022.packlet.catalog.controller;

import io.github.freitas022.packlet.catalog.dto.ProductDTO;
import io.github.freitas022.packlet.catalog.dto.ProductMinDTO;
import io.github.freitas022.packlet.catalog.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductMinDTO> findAll() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO findById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductMinDTO> findByName(@RequestParam(value = "name", defaultValue = "") String name) {
        return productService.findByName(name);
    }
}

package io.github.freitas022.packlet.catalog.service;

import io.github.freitas022.packlet.catalog.dto.ProductDTO;
import io.github.freitas022.packlet.catalog.dto.ProductMinDTO;
import io.github.freitas022.packlet.catalog.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    public List<ProductMinDTO> findAll() {
        return productRepository.findAll()
                .stream().map(product -> modelMapper.map(product, ProductMinDTO.class))
                .toList();
    }

    public ProductDTO findById(Long id) {
        return productRepository.findById(id)
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<ProductMinDTO> findByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name)
                .stream().map(product -> modelMapper.map(product, ProductMinDTO.class))
                .toList();
    }
}

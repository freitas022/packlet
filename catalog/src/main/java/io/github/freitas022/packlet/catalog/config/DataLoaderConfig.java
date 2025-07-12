package io.github.freitas022.packlet.catalog.config;

import io.github.freitas022.packlet.catalog.model.Category;
import io.github.freitas022.packlet.catalog.model.Product;
import io.github.freitas022.packlet.catalog.repository.CategoryRepository;
import io.github.freitas022.packlet.catalog.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Arrays;

@Configuration
public class DataLoaderConfig {

    @Bean
    public CommandLineRunner dataLoader(
            CategoryRepository categoryRepository,
            ProductRepository productRepository
    ) {
        return args -> {

            Category eletronics = new Category(null, "Eletrônicos");
            Category books = new Category(null, "Livros");
            Category looks = new Category(null, "Vestuário");


            categoryRepository.saveAll(Arrays.asList(eletronics, books, looks));

            Product smartphone = new Product();
            smartphone.setName("Smartphone X");
            smartphone.setLongDescription("Smartphone topo de linha com recursos avançados.");
            smartphone.setShortDescription("Smartphone moderno");
            smartphone.setImgUrl("https://img.com/smartphone.jpg");
            smartphone.setPrice(new BigDecimal("2999.99"));
            smartphone.setSku("SMX-2025");
            smartphone.setActive(true);
            smartphone.getCategories().add(eletronics);

            Product livroJava = new Product();
            livroJava.setName("Livro Java");
            livroJava.setLongDescription("Livro completo sobre Java e Spring.");
            livroJava.setShortDescription("Livro para desenvolvedores");
            livroJava.setImgUrl("https://img.com/livrojava.jpg");
            livroJava.setPrice(new BigDecimal("99.90"));
            livroJava.setSku("LJV-001");
            livroJava.setActive(true);
            livroJava.getCategories().add(books);

            Product camiseta = new Product();
            camiseta.setName("Camiseta Tech");
            camiseta.setLongDescription("Camiseta confortável para programadores.");
            camiseta.setShortDescription("Camiseta preta");
            camiseta.setImgUrl("https://img.com/camiseta.jpg");
            camiseta.setPrice(new BigDecimal("59.90"));
            camiseta.setSku("CAM-123");
            camiseta.setActive(true);
            camiseta.getCategories().add(looks);

            productRepository.saveAll(Arrays.asList(smartphone, livroJava, camiseta));
        };
    }
}

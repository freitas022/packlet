package io.github.freitas022.packlet.order.client;

import io.github.freitas022.packlet.order.dto.ProductDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class CatalogClient {

    @Autowired
    WebClient.Builder webClientBuilder;

    public Mono<ProductDTO> fetchProductById(Long id) {
        return webClientBuilder
                .baseUrl("lb://catalog")
                .build()
                .get()
                .uri("/api/products/{id}", id)
                .retrieve()
                .onStatus(
                        status -> status == HttpStatus.NOT_FOUND,
                        response -> Mono.error(new ResponseStatusException(
                                HttpStatus.BAD_REQUEST, "Produto com ID " + id + " não encontrado"
                        ))
                )
                .bodyToMono(ProductDTO.class);
    }
}

package io.github.freitas022.packlet.order.client;

import io.github.freitas022.packlet.order.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
public class UserClient {

    @Autowired
    WebClient.Builder webClientBuilder;

    public Mono<UserDTO> fetchUserById(Long id) {
        return webClientBuilder
                .baseUrl("lb://user")
                .build()
                .get()
                .uri("/api/users/{userId}", id)
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals,
                        response -> Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                "Usuário com ID " + id + " não encontrado.")))
                .bodyToMono(UserDTO.class);
    }
}

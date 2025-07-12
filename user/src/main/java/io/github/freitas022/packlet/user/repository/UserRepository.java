package io.github.freitas022.packlet.user.repository;

import io.github.freitas022.packlet.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByNameContainingIgnoreCase(String name);

    boolean existsByEmail(String email);
}

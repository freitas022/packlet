package io.github.freitas022.packlet.user.repository;

import io.github.freitas022.packlet.user.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}

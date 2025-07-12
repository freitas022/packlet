package io.github.freitas022.packlet.user.dto;

import io.github.freitas022.packlet.user.model.Address;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private LocalDate birthDate;
    private List<Address> addresses;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

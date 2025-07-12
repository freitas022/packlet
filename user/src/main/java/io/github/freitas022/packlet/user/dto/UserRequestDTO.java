package io.github.freitas022.packlet.user.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserRequestDTO {

    private String name;
    private String email;
    private String password;
    private String phone;
    private LocalDate birthDate;
}

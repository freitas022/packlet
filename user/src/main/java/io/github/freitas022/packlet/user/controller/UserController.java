package io.github.freitas022.packlet.user.controller;

import io.github.freitas022.packlet.user.dto.UserDTO;
import io.github.freitas022.packlet.user.dto.UserMinDTO;
import io.github.freitas022.packlet.user.dto.UserRequestDTO;
import io.github.freitas022.packlet.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserMinDTO> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<UserMinDTO> findByName(@RequestParam(value = "name", defaultValue = "") String name) {
        return userService.findByName(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO save(@RequestBody UserRequestDTO dto) {
        return userService.save(dto);
    }
}

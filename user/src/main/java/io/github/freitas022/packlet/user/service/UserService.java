package io.github.freitas022.packlet.user.service;

import io.github.freitas022.packlet.user.dto.UserDTO;
import io.github.freitas022.packlet.user.dto.UserMinDTO;
import io.github.freitas022.packlet.user.dto.UserRequestDTO;
import io.github.freitas022.packlet.user.model.User;
import io.github.freitas022.packlet.user.repository.UserRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    public List<UserMinDTO> findAll() {
        return userRepository.findAll()
                .stream().map(user -> modelMapper.map(user, UserMinDTO.class))
                .toList();
    }

    public UserDTO findById(Long id) {
        return userRepository.findById(id)
                .map(product -> modelMapper.map(product, UserDTO.class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<UserMinDTO> findByName(String name) {
        return userRepository.findByNameContainingIgnoreCase(name)
                .stream().map(user -> modelMapper.map(user, UserMinDTO.class))
                .toList();
    }

    public UserDTO save(@Valid UserRequestDTO dto) {
        var user = modelMapper.map(dto, User.class);

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }

        user = userRepository.save(user);

        return modelMapper.map(user, UserDTO.class);
    }
}

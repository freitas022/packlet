package io.github.freitas022.packlet.user.controller;

import io.github.freitas022.packlet.user.model.Address;
import io.github.freitas022.packlet.user.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/addresses")
public class AddressController {

    @Autowired
    AddressService addressService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Address> findAll() {
        return addressService.findAll();
    }
}

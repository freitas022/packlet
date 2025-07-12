package io.github.freitas022.packlet.user.service;

import io.github.freitas022.packlet.user.model.Address;
import io.github.freitas022.packlet.user.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    public List<Address> findAll() {
        return addressRepository.findAll();
    }
}

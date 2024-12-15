package com.example.sportshopv2.service;

import com.example.sportshopv2.model.Address;
import com.example.sportshopv2.repository.AddressRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    com.example.sportshopv2.service.KhachhangService khachhangService;


    public void updateAddress(Integer customerId, Integer addressId, String tinh, String quan, String phuong, String line) {
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new IllegalArgumentException("Invalid address ID"));
        address.setTinh(tinh);
        address.setQuan(quan);
        address.setPhuong(phuong);
        address.setLine(line);
        addressRepository.save(address);
    }



    public Address getAddressById(Integer addressId) {
        return addressRepository.findById(addressId).orElse(null);
    }
}

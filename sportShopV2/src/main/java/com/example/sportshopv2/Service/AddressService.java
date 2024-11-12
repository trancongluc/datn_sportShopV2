package com.example.sportshopv2.service;

import com.example.sportshopv2.Repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    KhachhangService khachhangService;


}

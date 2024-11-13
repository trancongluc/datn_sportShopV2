package com.example.sportshopv2.service;

import com.example.sportshopv2.Repository.AddressRepository;
import com.example.sportshopv2.Repository.KhachHangRepository;
import com.example.sportshopv2.entity.Address;
import com.example.sportshopv2.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    @Autowired
    AddressRepository addressRepository;

    @Autowired
    private KhachHangRepository customerRepository;



}

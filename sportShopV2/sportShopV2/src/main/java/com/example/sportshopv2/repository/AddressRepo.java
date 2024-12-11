package com.example.sportshopv2.repository;

import com.example.sportshopv2.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepo extends JpaRepository<Address, Integer> {
}

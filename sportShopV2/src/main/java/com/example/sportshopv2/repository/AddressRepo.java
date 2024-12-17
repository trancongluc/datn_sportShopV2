package com.example.sportshopv2.repository;

import com.example.sportshopv2.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepo extends JpaRepository<Address, Integer> {

    List<Address> findByKhachHang_Id(Integer id);
    Address findTop1ByKhachHang_Id(Integer id);
}

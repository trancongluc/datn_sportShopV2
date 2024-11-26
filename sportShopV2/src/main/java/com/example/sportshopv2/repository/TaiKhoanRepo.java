package com.example.sportshopv2.repository;

import com.example.sportshopv2.model.TaiKhoan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaiKhoanRepo extends JpaRepository<TaiKhoan, Integer> {
    TaiKhoan findTaiKhoanByUsername(String userName);
}

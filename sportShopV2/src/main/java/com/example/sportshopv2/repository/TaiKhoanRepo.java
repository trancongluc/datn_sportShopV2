package com.example.sportshopv2.repository;

import com.example.sportshopv2.model.NguoiDung;
import com.example.sportshopv2.model.TaiKhoan;
import com.example.sportshopv2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaiKhoanRepo extends JpaRepository<TaiKhoan, Integer> {
    TaiKhoan findByNguoiDung(NguoiDung nguoiDung);
    TaiKhoan findTaiKhoanByUsername(String userName);
    TaiKhoan findTaiKhoanById(Integer userName);
}

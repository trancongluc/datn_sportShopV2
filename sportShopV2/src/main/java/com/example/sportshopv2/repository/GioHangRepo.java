package com.example.sportshopv2.repository;

import com.example.sportshopv2.model.GioHang;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GioHangRepo extends JpaRepository<GioHang, Integer> {
    GioHang findByIdTaiKhoan_Id(Integer id);
}

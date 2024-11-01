package com.example.sportshopv2.Repository;

import com.example.sportshopv2.Entity.HoaDonChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HoaDonChiTietRepo extends JpaRepository<HoaDonChiTiet, Integer> {
    List<HoaDonChiTiet> findAllByHoaDon_Status(String status);
    HoaDonChiTiet findAllById(Integer id);
}

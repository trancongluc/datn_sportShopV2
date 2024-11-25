package com.example.sportshopv2.repository;

import com.example.sportshopv2.model.GioHangChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GioHangChiTietRepo extends JpaRepository<GioHangChiTiet, Integer> {
    GioHangChiTiet findByGioHang_IdAndSanPhamChiTiet_Id(Integer idGiohang, Integer idspct);
    List<GioHangChiTiet> findAllByGioHang_Id(Integer idGiohang);
}

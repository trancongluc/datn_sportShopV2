package com.example.sportshopv2.repository;

import com.example.sportshopv2.model.GioHangChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GioHangChiTietRepo extends JpaRepository<GioHangChiTiet, Integer> {
    GioHangChiTiet findByGioHang_IdAndSanPhamChiTiet_Id(Integer idGiohang, Integer idspct);

    List<GioHangChiTiet> findAllByGioHang_Id(Integer idGiohang);

    List<GioHangChiTiet> findAllByGioHang_IdTaiKhoan_Id(Integer idTaiKhoan);

    List<GioHangChiTiet> findAllBySanPhamChiTiet_IdInAndGioHang_IdTaiKhoan_Id(List<Long> ids, Integer id);

    List<GioHangChiTiet> findAllBySanPhamChiTiet_IdIn(List<Long> id);
}

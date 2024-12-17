package com.example.sportshopv2.repository;

import com.example.sportshopv2.model.GioHangChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GioHangChiTietRepo extends JpaRepository<GioHangChiTiet, Integer> {
    GioHangChiTiet findByGioHang_IdAndSanPhamChiTiet_Id(Integer idGiohang, Integer idspct);

    List<GioHangChiTiet> findAllByGioHang_Id(Integer idGiohang);

    List<GioHangChiTiet> findAllByGioHang_IdTaiKhoan_Id(Integer idTaiKhoan);

    List<GioHangChiTiet> findAllBySanPhamChiTiet_IdInAndGioHang_IdTaiKhoan_Id(List<Long> ids, Integer id);

    List<GioHangChiTiet> findAllBySanPhamChiTiet_IdIn(List<Long> id);

    @Modifying
    @Query("DELETE FROM GioHangChiTiet g WHERE g.sanPhamChiTiet.id IN :productIds")
    void deleteBySanPhamChiTietIds(@Param("productIds") List<Long> productIds);
}

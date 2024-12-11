package com.example.sportshopv2.repository;

import com.example.sportshopv2.model.GioHangChiTiet;
import com.example.sportshopv2.model.PhieuGiamGiaKhachHang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhieuGiamGiaKhachHangRepository extends JpaRepository<PhieuGiamGiaKhachHang, Integer> {
    List<PhieuGiamGiaKhachHang> findAllByIdTaiKhoan_IdAndDeleted(Integer id, Boolean deleted);

    PhieuGiamGiaKhachHang findByIdPhieuGiamGia_Id(Integer id);

}

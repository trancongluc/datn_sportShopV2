package com.example.sportshopv2.Repository;

import com.example.sportshopv2.Entity.HoaDonChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HoaDonChiTietRepo extends JpaRepository<HoaDonChiTiet, Integer> {
    List<HoaDonChiTiet> findAllByHoaDon_Status(String status);
    HoaDonChiTiet findAllById(Integer id);
    List<HoaDonChiTiet> findAllByHoaDon_Id(Integer id);
    List<HoaDonChiTiet> findAllByHoaDon_Type(String type);
//    List<HoaDonChiTiet> findAllByHoaDon_Bill_code(String billCode);
}

package com.example.sportshopv2.repository;

import com.example.sportshopv2.model.SPCT;
import com.example.sportshopv2.model.SanPhamChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SPCTRePo extends JpaRepository<SPCT, Integer> {
    List<SPCT> findByIdIn(List<Long> ids);
    List<SPCT> findByidSanPham_IdIn(List<Long> ids);
}

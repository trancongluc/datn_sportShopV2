package com.example.sportshopv2.repository;

import com.example.sportshopv2.model.DotGiamGia;
import com.example.sportshopv2.model.SanPham;
import com.example.sportshopv2.model.SanPhamChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DotGiamGiaRepo extends JpaRepository<DotGiamGia, Integer> {

    Page<SanPham> findAllByOrderByCreateAtDesc(Pageable pageable);

    @Query("SELECT dgc.spct FROM DotGiamGiaChiTiet dgc WHERE dgc.dotGiamGia.id = :dotGiamGiaId")
    List<SanPhamChiTiet> findSanPhamChiTietByDotGiamGiaId(@Param("dotGiamGiaId") Integer dotGiamGiaId);



}

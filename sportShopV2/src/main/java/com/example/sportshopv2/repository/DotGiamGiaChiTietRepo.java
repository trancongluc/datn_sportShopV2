package com.example.sportshopv2.repository;

import com.example.sportshopv2.model.DotGiamGiaChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DotGiamGiaChiTietRepo extends JpaRepository<DotGiamGiaChiTiet, Integer> {
    List<DotGiamGiaChiTiet> findAllByDotGiamGia_IdAndStatus(Integer idDotGiamgia, String status);


    List<DotGiamGiaChiTiet> findAllByDotGiamGia_Id(Integer dotGiamGiaId);

    List<DotGiamGiaChiTiet> findAllByStatus(String status);

}

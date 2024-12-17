package com.example.sportshopv2.repository;

import com.example.sportshopv2.model.DotGiamGiaChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DotGiamGiaChiTietRepo extends JpaRepository<DotGiamGiaChiTiet, Integer> {
    List<DotGiamGiaChiTiet> findAllByDotGiamGia_IdAndStatus(Integer idDotGiamgia, String status);
<<<<<<< HEAD

    List<DotGiamGiaChiTiet> findAllByDotGiamGia_Id(Integer dotGiamGiaId);
=======
    List<DotGiamGiaChiTiet> findAllByStatus(String status);
>>>>>>> 779f79e8a7a39c5bfa5af542f9d14fde0bdd1a8f
}

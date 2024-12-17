package com.example.sportshopv2.repository;

import com.example.sportshopv2.model.Diachi;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaChiRepository extends JpaRepository<Diachi, Integer> {
    Diachi findDiachiById (Integer idDiaChi);
}

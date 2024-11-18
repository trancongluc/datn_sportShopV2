package com.example.sportshopv2.repository;

import com.example.sportshopv2.model.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HoaDonRepo extends JpaRepository<HoaDon, Integer> {

    HoaDon findAllById(Integer id);
//    Optional<HoaDon> findById(Integer id);
    List<HoaDon> findAllByStatus( String status);
}

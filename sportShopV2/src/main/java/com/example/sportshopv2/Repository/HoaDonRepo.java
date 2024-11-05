package com.example.sportshopv2.Repository;

import com.example.sportshopv2.Entity.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HoaDonRepo extends JpaRepository<HoaDon, Integer> {

    HoaDon findAllById(Integer id);
//    Optional<HoaDon> findById(Integer id);
}

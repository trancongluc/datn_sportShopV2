package com.example.sportshopv2.repository;

import com.example.sportshopv2.entity.MauSac;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MauSacRepository extends JpaRepository<MauSac, Integer> {
    List<MauSac> findAllByOrderByCreateAtDesc( );
    Optional<MauSac> findById(Integer id);

}

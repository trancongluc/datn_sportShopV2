package com.example.sportshopv2.repository;

import com.example.sportshopv2.model.TheLoai;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TheLoaiRepository extends JpaRepository<TheLoai, Integer> {
    Page<TheLoai> findAllByOrderByCreateAtDesc(Pageable pageable);
    List<TheLoai> findAllByOrderByCreateAtDesc();
    Optional<TheLoai> findById(Integer id);
}

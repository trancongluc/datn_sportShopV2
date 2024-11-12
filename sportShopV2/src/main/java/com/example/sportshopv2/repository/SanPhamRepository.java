package com.example.sportshopv2.repository;

import com.example.sportshopv2.entity.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SanPhamRepository extends JpaRepository<SanPham, Integer> {
    Page<SanPham> findAllByOrderByCreateAtDesc(Pageable pageable);
    Optional<SanPham> findById(Integer id);
}

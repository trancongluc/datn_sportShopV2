package com.example.sportshopv2.repository;

import com.example.sportshopv2.model.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SanPhamRepository extends JpaRepository<SanPham, Integer> {
    Page<SanPham> findAllByOrderByCreateAtDesc(Pageable pageable);

    List<SanPham> findAllByOrderByCreateAtDesc();

    Optional<SanPham> findById(Integer id);

    SanPham findAllById(Integer id);
}

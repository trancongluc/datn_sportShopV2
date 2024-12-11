package com.example.sportshopv2.repository;

import com.example.sportshopv2.model.ThuongHieu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ThuongHieuRepository extends JpaRepository<ThuongHieu, Integer> {
    Page<ThuongHieu> findAllByOrderByCreateAtDesc(Pageable pageable);
    List<ThuongHieu> findAllByOrderByCreateAtDesc();
    Optional<ThuongHieu> findById(Integer id);
}

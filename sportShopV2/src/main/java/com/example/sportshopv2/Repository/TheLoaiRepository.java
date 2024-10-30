package com.example.sportshopv2.Repository;

import com.example.sportshopv2.model.ChatLieu;
import com.example.sportshopv2.model.TheLoai;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TheLoaiRepository extends JpaRepository<TheLoai, Integer> {
    Page<TheLoai> findAllByOrderByCreateAtDesc(Pageable pageable);
    Optional<TheLoai> findById(Integer id);
}

package com.example.sportshopv2.repository;

import com.example.sportshopv2.model.ChatLieu;
import com.example.sportshopv2.model.CoGiay;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CoGiayRepository extends JpaRepository<CoGiay, Integer> {
    Page<CoGiay> findAllByOrderByCreateAtDesc(Pageable pageable);
    List<CoGiay> findAllByOrderByCreateAtDesc();
    Optional<CoGiay> findById(Integer id);

    @Query("SELECT cg FROM CoGiay cg ORDER BY cg.createAt DESC ")
    List<ChatLieu> findAllChatLieuByCreatedAtDesc();
}

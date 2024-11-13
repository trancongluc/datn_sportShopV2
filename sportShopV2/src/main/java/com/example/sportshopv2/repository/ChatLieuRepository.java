package com.example.sportshopv2.repository;

import com.example.sportshopv2.model.ChatLieu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatLieuRepository extends JpaRepository<ChatLieu, Integer> {
    Page<ChatLieu> findAllByOrderByCreateAtDesc(Pageable pageable);
    Optional<ChatLieu> findById(Integer id);
    List<ChatLieu> findAllByOrderByCreateAtDesc();
    @Query("SELECT c FROM ChatLieu c ORDER BY c.createAt DESC ")
    List<ChatLieu> findAllChatLieuByCreatedAtDesc();
}

package com.example.sportshopv2.repository;


import com.example.sportshopv2.model.chatBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ChatBoxRepository extends JpaRepository<chatBox, Integer> {
    List<chatBox> findByDeletedFalse();
}
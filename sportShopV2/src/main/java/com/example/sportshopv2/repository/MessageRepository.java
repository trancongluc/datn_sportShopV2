package com.example.sportshopv2.repository;


import com.example.sportshopv2.model.message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<message, Integer> {
    List<message> findByChatBoxId(int chatBoxId);
}

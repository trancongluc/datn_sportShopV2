package com.example.sportshopv2.repository;


import com.example.sportshopv2.model.chatBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ChatBoxRepository extends JpaRepository<chatBox, Integer> {
    @Query("SELECT c FROM chatBox c LEFT JOIN FETCH c.messages WHERE c.deleted = false")
    List<chatBox> findWithMessagesByDeletedFalse();

}
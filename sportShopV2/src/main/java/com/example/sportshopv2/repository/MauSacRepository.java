package com.example.sportshopv2.Repository;

import com.example.sportshopv2.model.ChatLieu;
import com.example.sportshopv2.model.MauSac;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MauSacRepository extends JpaRepository<MauSac, Integer> {
    List<MauSac> findAllByOrderByCreateAtDesc( );
    Optional<MauSac> findById(Integer id);

}

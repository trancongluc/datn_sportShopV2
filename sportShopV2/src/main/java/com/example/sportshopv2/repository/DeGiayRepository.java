package com.example.sportshopv2.repository;

import com.example.sportshopv2.model.DeGiay;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeGiayRepository extends JpaRepository<DeGiay, Integer> {
    Page<DeGiay> findAllByOrderByCreateAtDesc(Pageable pageable);
    List<DeGiay> findAllByOrderByCreateAtDesc( );
    Optional<DeGiay> findById(Integer id);


}
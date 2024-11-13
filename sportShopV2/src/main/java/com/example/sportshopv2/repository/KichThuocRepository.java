package com.example.sportshopv2.repository;

import com.example.sportshopv2.model.KichThuoc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KichThuocRepository extends JpaRepository<KichThuoc, Integer> {
    Page<KichThuoc> findAllByOrderByCreateAtDesc(Pageable pageable);
    Optional<KichThuoc> findById(Integer id);


}

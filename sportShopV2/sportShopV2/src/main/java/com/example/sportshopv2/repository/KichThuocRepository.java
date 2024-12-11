package com.example.sportshopv2.repository;

import com.example.sportshopv2.model.KichThuoc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface KichThuocRepository extends JpaRepository<KichThuoc, Integer> {
    List<KichThuoc> findAllByOrderByCreateAtDesc();
    Optional<KichThuoc> findById(Integer id);


}

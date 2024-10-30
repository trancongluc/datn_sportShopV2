package com.example.sportshopv2.Repository;

import com.example.sportshopv2.model.ChatLieu;
import com.example.sportshopv2.model.KichThuoc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface KichThuocRepository extends JpaRepository<KichThuoc, Integer> {
    Page<KichThuoc> findAllByOrderByCreateAtDesc(Pageable pageable);
    Optional<KichThuoc> findById(Integer id);


}

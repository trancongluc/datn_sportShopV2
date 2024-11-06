package com.example.sportshopv2.Repository;

import com.example.sportshopv2.model.SanPhamChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SanPhamChiTietRepository extends JpaRepository<SanPhamChiTiet, Integer> {
    Page<SanPhamChiTiet> findAllByOrderByCreateAtDesc(Pageable pageable);

}

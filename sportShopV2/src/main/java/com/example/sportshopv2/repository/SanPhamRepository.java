package com.example.sportshopv2.repository;

import com.example.sportshopv2.model.SanPham;
import com.example.sportshopv2.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SanPhamRepository extends JpaRepository<SanPham, Integer> {
    Page<SanPham> findAllByOrderByCreateAtDesc(Pageable pageable);

    List<SanPham> findAllByOrderByCreateAtDesc();

    Optional<SanPham> findById(Integer id);

    SanPham findAllById(Integer id);

    @Query("SELECT u FROM SanPham u WHERE " +
            "(u.tenSanPham LIKE %:keyword% OR u.code LIKE %:keyword%) AND " +
            "(u.trangThai LIKE %:status% OR :status = 'all')")
    Page<SanPham> searchSP(@Param("keyword") String keyword, @Param("status") String status, Pageable pageable);

}

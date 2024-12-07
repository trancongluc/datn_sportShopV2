package com.example.sportshopv2.repository;

import com.example.sportshopv2.model.HoaDon;
import com.example.sportshopv2.model.SanPhamChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SanPhamChiTietRepository extends JpaRepository<SanPhamChiTiet, Integer> {

    Page<SanPhamChiTiet> findAllByDeletedAndIdSanPham(boolean deleted, Integer idSanPham, Pageable pageable);

    List<SanPhamChiTiet> findAllByDeleted(boolean deleted);

    SanPhamChiTiet findByIdAndDeleted(Integer id, boolean delete);


}

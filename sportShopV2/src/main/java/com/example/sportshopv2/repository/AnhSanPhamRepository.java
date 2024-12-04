package com.example.sportshopv2.repository;

import com.example.sportshopv2.model.AnhSanPham;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnhSanPhamRepository extends JpaRepository<AnhSanPham, Integer> {
    List<AnhSanPham> findBySanPhamChiTiet_Id(Integer idSPCT);

}

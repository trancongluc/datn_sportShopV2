package com.example.sportshopv2.repository;

import com.example.sportshopv2.model.HoaDonChiTiet;
import com.example.sportshopv2.model.SanPhamChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SanPhamChiTietRepository extends JpaRepository<SanPhamChiTiet, Integer> {

    Page<SanPhamChiTiet> findAllByDeletedAndIdSanPham(boolean deleted, Integer idSanPham, Pageable pageable);

    List<SanPhamChiTiet> findAllByDeleted(boolean deleted);

    List<SanPhamChiTiet> findAllByDeletedAndIdSanPham(boolean deleted, Integer idSP);

    SanPhamChiTiet findByIdAndDeleted(Integer id, boolean delete);

    SanPhamChiTiet findAllByDeletedAndIdSanPhamAndIdKichThuocAndIdMauSac(boolean deleted, Integer idSP, Integer idKhichThuoc, Integer idMauSac);

    @Query("SELECT h FROM SanPhamChiTiet h WHERE h.id IN (SELECT MIN(hd.id) FROM  SanPhamChiTiet hd GROUP BY hd.idSanPham)")
    List<SanPhamChiTiet> findDistinctByIdProduct();

}

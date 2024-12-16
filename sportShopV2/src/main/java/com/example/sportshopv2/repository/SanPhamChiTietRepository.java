package com.example.sportshopv2.repository;

import com.example.sportshopv2.model.AnhSanPham;
import com.example.sportshopv2.model.SanPhamChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SanPhamChiTietRepository extends JpaRepository<SanPhamChiTiet, Integer> {

    Page<SanPhamChiTiet> findAllByDeletedAndIdSanPham(boolean deleted, Integer idSanPham, Pageable pageable);

    List<SanPhamChiTiet> findAllByDeletedAndTrangThaiOrderByCreateAtDesc(boolean deleted, String trangThai);

    List<SanPhamChiTiet> findAllByDeletedAndIdSanPham(boolean deleted, Integer idSP);
    List<SanPhamChiTiet> findAllByDeletedAndIdSanPhamAndId(boolean deleted, Integer idSP, Integer IdSPCT);
    SanPhamChiTiet findByIdAndDeleted(Integer id, boolean delete);

    SanPhamChiTiet findAllByDeletedAndIdSanPhamAndIdKichThuocAndIdMauSac(boolean deleted, Integer idSP, Integer idKhichThuoc, Integer idMauSac);

    @Query("SELECT h FROM SanPhamChiTiet h WHERE h.id IN " +
            "(SELECT MIN(hd.id) FROM SanPhamChiTiet hd WHERE hd.deleted = false GROUP BY hd.idSanPham)")
    List<SanPhamChiTiet> findDistinctByIdAndIdSanPham();

    List<SanPhamChiTiet> findByIdIn(List<Long> ids);

    @Query("SELECT COALESCE(SUM(pd.soLuong), 0) " +
            "FROM SanPhamChiTiet pd " +
            "WHERE pd.idSanPham = :productId")
    Integer tongSoLuongSP(@Param("productId") Integer productId);

    @Query("SELECT s FROM SanPhamChiTiet s WHERE s.id = :id AND s.deleted = false")
    Optional<SanPhamChiTiet> findActiveById(@Param("id") Integer id);
    @Query("SELECT DISTINCT ms.tenMauSac " +
            "FROM SanPhamChiTiet pd " +
            "JOIN MauSac ms ON ms.id = pd.idMauSac " +
            "WHERE pd.idSanPham = :productId")
    List<String> findDistinctColorsByProductId(@Param("productId") Integer productId);

}

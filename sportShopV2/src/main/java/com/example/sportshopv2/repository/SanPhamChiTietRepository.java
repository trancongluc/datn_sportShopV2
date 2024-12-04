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

    Page<SanPhamChiTiet> findAllByDeletedAndSanPham(boolean deleted, Integer idSanPham, Pageable pageable);
//    @Query("SELECT pd FROM SanPhamChiTiet pd " +
//            "LEFT JOIN pd.kichThuoc s " +
//            "LEFT JOIN pd.mauSac c " +
//            "LEFT JOIN pd.sanPham b " +
//            "WHERE pd.id = :id")
//    Optional<SanPhamChiTiet> findProductDetailWithRelations(@Param("id") Integer id);

    List<SanPhamChiTiet> findAllByDeleted(boolean deleted);
    SanPhamChiTiet findByIdAndDeleted(Integer id, boolean delete);
    Optional<SanPhamChiTiet> findById(Integer id);

}

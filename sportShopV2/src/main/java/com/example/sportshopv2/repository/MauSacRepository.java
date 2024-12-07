package com.example.sportshopv2.repository;

import com.example.sportshopv2.model.KichThuoc;
import com.example.sportshopv2.model.MauSac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MauSacRepository extends JpaRepository<MauSac, Integer> {
    List<MauSac> findAllByOrderByCreateAtDesc( );
    Optional<MauSac> findById(Integer id);
    @Query("SELECT ms " +
            "FROM MauSac ms " +
            "WHERE ms.id NOT IN (" +
            "   SELECT pd.idMauSac " +
            "   FROM SanPhamChiTiet pd " +
            "   WHERE pd.idSanPham = :productId" +
            ")")
    List<MauSac> findColorsNotInProduct(@Param("productId") Integer productId);
}

package com.example.sportshopv2.repository;

import com.example.sportshopv2.model.KichThuoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface KichThuocRepository extends JpaRepository<KichThuoc, Integer> {
    List<KichThuoc> findAllByOrderByCreateAtDesc();
    Optional<KichThuoc> findById(Integer id);
    @Query("SELECT s " +
            "FROM KichThuoc s " +
            "WHERE s.id NOT IN (" +
            "   SELECT pd.idKichThuoc " +
            "   FROM SanPhamChiTiet pd " +
            "   WHERE pd.idSanPham = :productId" +
            ")")
    List<KichThuoc> findSizesNotInProduct(@Param("productId") Integer productId);

    @Query("SELECT k FROM KichThuoc k WHERE k = :kichThuoc")
    KichThuoc findByIDKT(@Param("kichThuoc") KichThuoc kichThuoc);



}

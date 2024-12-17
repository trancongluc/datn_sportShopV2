package com.example.sportshopv2.repository;

import com.example.sportshopv2.model.PhieuGiamGia;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PhieuGiamGiaResponsitory extends JpaRepository<PhieuGiamGia, Integer> {
    boolean existsByVoucherCode(String voucherCode);

    List<PhieuGiamGia> findByMinimumValueLessThan(Integer discountValue);

    @Query("SELECT v FROM PhieuGiamGia v WHERE v.status LIKE :status AND v.minimumValue <= :minimumValue")
    List<PhieuGiamGia> findVoucherByStatusAndMinimumValue(@Param("status") String status, @Param("minimumValue") Integer minimumValue);


    List<PhieuGiamGia> findAll(Sort sort);


    List<PhieuGiamGia> findByEndDateBeforeAndStatusNot(LocalDateTime now, String status);
}

package com.example.sportshopv2.repository;

import com.example.sportshopv2.model.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import java.util.List;

public interface HoaDonRepo extends JpaRepository<HoaDon, Integer> {

    HoaDon findAllById(Integer id);

    List<HoaDon> findAllByStatusNotOrderByCreateAtDesc(String status);

    List<HoaDon> findAllByType(String Type);

    List<HoaDon> findAllByCreateAtBetween(LocalDateTime start, LocalDateTime end);
    List<HoaDon> findHoaDonByBillCodeLike(String billCode);
    List<HoaDon> findAllByStatusLikeOrderByCreateAtDesc(String status);
    @Query("SELECT h FROM HoaDon h WHERE " +
            "(:maHoaDon IS NULL OR h.billCode LIKE %:maHoaDon%) AND " +
            "(:Type IS NULL OR h.type = :Type) AND " +
            "(h.createAt BETWEEN :startDate AND :endDate)")
    List<HoaDon> searchHoaDon(@Param("maHoaDon") String maHoaDon,
                              @Param("Type") String Type,
                              @Param("startDate") LocalDateTime startDate,
                              @Param("endDate") LocalDateTime endDate);


//    Optional<HoaDon> findById(Integer id);
    List<HoaDon> findAllByStatus( String status);

    @Query("SELECT h FROM HoaDon h WHERE h.id_account.id = :id")
    List<HoaDon> findByCustomerId(@Param("id") Integer id);
    int countByStatus(String status);
}

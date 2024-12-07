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
    List<HoaDon> findAllByStatus(String status);

    @Query("SELECT h FROM HoaDon h WHERE h.id_account.id = :id")
    List<HoaDon> findByCustomerId(@Param("id") Integer id);

    int countByStatus(String status);


    @Query("SELECT b FROM HoaDon b " +
            "JOIN b.id_account a " +
            "JOIN a.nguoiDung u " +
            "LEFT JOIN Address ad ON ad.khachHang.id = u.id " +
            "JOIN HoaDonChiTiet bd on bd.hoaDon.id = b.id " +
            "JOIN SanPhamChiTiet pd on pd.id = bd.sanPhamChiTiet.id " +
            "JOIN MauSac c on c.id=pd.idMauSac " +
            "WHERE b.billCode = :tenHoaDon")
    HoaDon findBillDetailByBillCode(@Param("tenHoaDon") String tenHoaDon);

    @Query("SELECT SUM(bd.quantity) AS totalQuantity " +
            "FROM HoaDonChiTiet bd " +
            "JOIN bd.hoaDon b " +
            "WHERE MONTH(b.createAt) = :month AND YEAR(b.createAt) = :year and b.status = 'Hoàn thành'")
    Integer getTotalQuantityForMonthAndYear(@Param("month") int month, @Param("year") int year);

    @Query("SELECT COUNT(b) FROM HoaDon b WHERE MONTH(b.createAt) = :month AND YEAR(b.createAt) = :year and b.status = 'Hoàn thành'")
    Integer countBillsByMonth(@Param("month") int month, @Param("year") int year);

    @Query("SELECT SUM(bd.quantity) FROM HoaDonChiTiet bd " +
            "JOIN bd.hoaDon b " +
            "WHERE MONTH(b.createAt) = :month AND YEAR(b.createAt) = :year and b.status = 'Hoàn thành'")
    Integer countProductsByMonth(@Param("month") int month, @Param("year") int year);

    @Query("SELECT COUNT(b) FROM HoaDon b WHERE DATE(b.createAt) = :date")
    Integer countBillsByDay(@Param("date") LocalDate date);

    @Query("SELECT SUM(bd.quantity) FROM HoaDonChiTiet bd " +
            "JOIN bd.hoaDon b " +
            "WHERE DATE(b.createAt) = :date")
    Integer countProductsByDay(@Param("date") LocalDate date);

    // tuần
    @Query("SELECT COUNT(b) FROM HoaDon b WHERE  b.status = 'Hoàn thành' and b.createAt BETWEEN :startDate AND :endDate ")
    Integer countBillsLast7Days(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT SUM(bd.quantity) FROM HoaDonChiTiet bd " +
            "JOIN bd.hoaDon b " +
            "WHERE b.status = 'Hoàn thành' and b.createAt BETWEEN :startDate AND :endDate")
    Integer countProductsLast7Days(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    //tháng
    @Query("SELECT DAY(b.createAt) AS day, COUNT(b) AS billCount " +
            "FROM HoaDon b " +
            "WHERE b.status = 'Hoàn thành' and MONTH(b.createAt) = :month AND YEAR(b.createAt) = :year " +
            "GROUP BY DAY(b.createAt) " +
            "ORDER BY DAY(b.createAt)")
    List<Object[]> countBillsByDayInMonth(@Param("month") int month, @Param("year") int year);

    @Query("SELECT DAY(b.createAt) AS day, SUM(bd.quantity) AS productCount " +
            "FROM HoaDonChiTiet bd " +
            "JOIN bd.hoaDon b " +
            "WHERE b.status = 'Hoàn thành' and MONTH(b.createAt) = :month AND YEAR(b.createAt) = :year " +
            "GROUP BY DAY(b.createAt) " +
            "ORDER BY DAY(b.createAt)")
    List<Object[]> countProductsByDayInMonth(@Param("month") int month, @Param("year") int year);


}

package com.example.sportshopv2.repository;

import com.example.sportshopv2.model.HoaDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import java.util.List;

public interface HoaDonRepo extends JpaRepository<HoaDon, Integer> {

    HoaDon findAllById(Integer id);

//    List<HoaDon> findAllByStatusNotOrderByCreateAtDesc(String status);
    Page<HoaDon> findAllByStatusNotOrderByCreateAtDesc(String status, Pageable pageable);
    List<HoaDon> findAllByType(String Type);

    List<HoaDon> findAllByCreateAtBetween(LocalDateTime start, LocalDateTime end);

    List<HoaDon> findHoaDonByBillCodeLike(String billCode);

//    List<HoaDon> findAllByStatusLikeOrderByCreateAtDesc(String status);
    Page<HoaDon> findAllByStatusLikeOrderByCreateAtDesc(String status, Pageable pageable);
    @Query("SELECT h FROM HoaDon h WHERE " +
            "(:userName IS NULL OR h.user_name LIKE %:userName%) AND " +
            "(:Type IS NULL OR h.type = :Type) AND " +
            "(h.createAt BETWEEN :startDate AND :endDate)")
    List<HoaDon> searchHoaDon(@Param("userName") String maHoaDon,
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
            "WHERE MONTH(b.receive_date) = :month AND YEAR(b.receive_date) = :year and b.status = 'Hoàn thành'")
    Integer getTotalQuantityForMonthAndYear(@Param("month") int month, @Param("year") int year);

    @Query("SELECT COUNT(b) FROM HoaDon b WHERE MONTH(b.receive_date) = :month AND YEAR(b.receive_date) = :year and b.status = 'Hoàn thành'")
    Integer countBillsByMonth(@Param("month") int month, @Param("year") int year);

    @Query("SELECT SUM(bd.quantity) FROM HoaDonChiTiet bd " +
            "JOIN bd.hoaDon b " +
            "WHERE MONTH(b.receive_date) = :month AND YEAR(b.receive_date) = :year and b.status = 'Hoàn thành'")
    Integer countProductsByMonth(@Param("month") int month, @Param("year") int year);

    @Query("SELECT COUNT(b) FROM HoaDon b WHERE DATE(b.createAt) = :date")
    Integer countBillsByDay(@Param("date") LocalDate date);

    @Query("SELECT SUM(bd.quantity) FROM HoaDonChiTiet bd " +
            "JOIN bd.hoaDon b " +
            "WHERE DATE(b.receive_date) = :date")
    Integer countProductsByDay(@Param("date") LocalDate date);


    //tháng
    @Query("SELECT DAY(b.receive_date) AS day, COUNT(b) AS billCount " +
            "FROM HoaDon b " +
            "WHERE b.status = 'Hoàn thành' and MONTH(b.receive_date) = :month AND YEAR(b.receive_date) = :year " +
            "GROUP BY DAY(b.receive_date) " +
            "ORDER BY DAY(b.receive_date)")
    List<Object[]> countBillsByDayInMonth(@Param("month") int month, @Param("year") int year);

    @Query("SELECT DAY(b.receive_date) AS day, SUM(bd.quantity) AS productCount " +
            "FROM HoaDonChiTiet bd " +
            "JOIN bd.hoaDon b " +
            "WHERE b.status = 'Hoàn thành' and MONTH(b.receive_date) = :month AND YEAR(b.receive_date) = :year " +
            "GROUP BY DAY(b.receive_date) " +
            "ORDER BY DAY(b.receive_date)")
    List<Object[]> countProductsByDayInMonth(@Param("month") int month, @Param("year") int year);

    //ngày
    @Query("SELECT HOUR(b.receive_date) AS hour, COUNT(b) AS billCount " +
            "FROM HoaDon b " +
            "WHERE b.status = 'Hoàn thành' AND CAST(b.receive_date AS DATE) = :date " +
            "GROUP BY HOUR(b.receive_date) " +
            "ORDER BY HOUR(b.receive_date)")
    List<Object[]> countBillsByHour(@Param("date") LocalDate date);

    @Query("SELECT HOUR(b.receive_date) AS hour, SUM(bd.quantity) AS productCount " +
            "FROM HoaDonChiTiet bd " +
            "JOIN bd.hoaDon b " +
            "WHERE b.status = 'Hoàn thành' AND CAST(b.receive_date AS DATE) = :date " +
            "GROUP BY HOUR(b.receive_date) " +
            "ORDER BY HOUR(b.receive_date)")
    List<Object[]> countProductsByHour(@Param("date") LocalDate date);
    // Thống kê hóa đơn theo ngày trong khoảng thời gian
    @Query("SELECT CAST(b.receive_date AS date) AS day, COUNT(b) AS billCount " +
            "FROM HoaDon b " +
            "WHERE b.status = 'Hoàn thành' AND b.receive_date BETWEEN :startDate AND :endDate " +
            "GROUP BY CAST(b.receive_date AS date) ORDER BY CAST(b.receive_date AS date)")
    List<Object[]> countBillsByDayInDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // Thống kê sản phẩm theo ngày trong khoảng thời gian
    @Query("SELECT CAST(b.receive_date AS date) AS day, SUM(bd.quantity) AS productCount " +
            "FROM HoaDonChiTiet bd " +
            "JOIN bd.hoaDon b " +
            "WHERE b.status = 'Hoàn thành' AND b.receive_date BETWEEN :startDate AND :endDate " +
            "GROUP BY CAST(b.receive_date AS date) ORDER BY CAST(b.receive_date AS date)")
    List<Object[]> countProductsByDayInDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    //Loại bỏ nếu các chức năng khác bị ảnh hưởng
    @Query(value = "SELECT b.id, b.user_name, b.create_at, b.status, b.total_money, b.address, p.name " +
            "FROM Bill b " +
            "JOIN Bill_Detail bd ON b.ID = bd.id_bill " +
            "JOIN Product_detail pd ON bd.id_product_detail = pd.ID " +
            "JOIN Product p ON pd.id_product = p.ID " +
            "WHERE b.id_staff = :id", nativeQuery = true)
    List<Object[]> getHistory(Integer id);

    //Loại bỏ nếu các chức năng khác bị ảnh hưởng
    @Query(value = "SELECT b.id, b.user_name, b.create_at, b.status, b.total_money, b.address, p.name " +
            "FROM Bill b " +
            "JOIN Bill_Detail bd ON b.id = bd.id_bill " +
            "JOIN Product_detail pd ON bd.id_product_detail = pd.id " +
            "JOIN Product p ON pd.id_product = p.id " +
            "WHERE b.id_staff = :id AND b.status LIKE CONCAT('%', :trangthai, '%')", nativeQuery = true)
    List<Object[]> SearchHistory(@Param("id") Integer id, @Param("trangthai") String trangthai);
}

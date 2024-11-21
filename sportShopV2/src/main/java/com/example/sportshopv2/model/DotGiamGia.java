package com.example.sportshopv2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Sale")
public class DotGiamGia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "sale_name")
    private String name;
    @Column(name = "sale_code")
    private String saleCode;
    @Column(name = "discount_value")
    private BigDecimal discountValue;

    @Column(name = "start_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startDate;

    @Column(name = "end_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endDate;

    @Column(name = "update_at")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime updateAt;

    @ManyToOne
    @JoinColumn(name = "voucher_id")
    private PhieuGiamGia phieu;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private SanPham sanPham;

    @ManyToOne
    @JoinColumn(name = "detail_id")
    private SanPhamChiTiet spct;

    public boolean isActive() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("Thời gian hiện tại: " + now); // In ra thời gian hiện tại
        return startDate != null && endDate != null && startDate.isBefore(now) && endDate.isAfter(now);
    }


    public String getStatus() {
        return isActive() ? "Đang diễn ra" : "Kết thúc";
    }


}
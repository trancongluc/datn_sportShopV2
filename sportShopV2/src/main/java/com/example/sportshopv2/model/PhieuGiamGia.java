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
@Table(name = "Voucher")
public class PhieuGiamGia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "voucher_code")
    private String voucherCode;

    @Column(name = "name")
    private String name;

    @Column(name = "discount_value")
    private BigDecimal discountValue;

    @Column(name = "minimum_value")
    private BigDecimal minimumValue;

    @Column(name = "form_voucher")
    private String formVoucher;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "start_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startDate;

    @Column(name = "end_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endDate;




    public boolean isActive() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("Thời gian hiện tại: " + now); // In ra thời gian hiện tại
        return startDate != null && endDate != null && startDate.isBefore(now) && endDate.isAfter(now);
    }


    public String getStatus() {
        return isActive() ? "Đang diễn ra" : "Kết thúc";
    }
}
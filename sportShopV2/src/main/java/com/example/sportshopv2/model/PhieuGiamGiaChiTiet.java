package com.example.sportshopv2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Voucher_Detail")
public class PhieuGiamGiaChiTiet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @JoinColumn(name = "id_voucher")
    @ManyToOne
    private PhieuGiamGia idVoucher;
    @JoinColumn(name = "id_bill")
    @ManyToOne
    private HoaDon idBill;
    @Column(name = "before_price")
    private BigDecimal before_price;

    @Column(name = "after_price")
    private BigDecimal after_price;

    /*@ManyToOne
    @JoinColumn(name = "id_voucher")
    private PhieuGiamGia phieu;*/
     @Column(name = "value_voucher")
    private BigDecimal valueVoucher;
    @Column(name = "create_at", nullable = false)
    private LocalDateTime createAt = LocalDateTime.now();

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "update_at")
    private LocalDateTime updateAt = LocalDateTime.now();

    @Column(name = "update_by")
    private String updateBy;
}

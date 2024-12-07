package com.example.sportshopv2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Account_Voucher")
public class PhieuGiamGiaKhachHang extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "id_account")
    private TaiKhoan idTaiKhoan;
    @ManyToOne
    @JoinColumn(name = "id_voucher")
    private PhieuGiamGia idPhieuGiamGia;
    @Column(name ="status")
    private String trangThai;
}

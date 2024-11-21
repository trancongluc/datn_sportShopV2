package com.example.sportshopv2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Cart_Detail")
public class GioHangChiTiet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "id_cart")
    private GioHang gioHang;
    @ManyToOne
    @JoinColumn(name = "id_product_detail")
    private SPCT sanPhamChiTiet;
    @Column(name = "quantity")
    private Integer soLuong;
    @Column(name = "price")
    private Float giaTien;
    @Column(name = "status")
    private String trangThai;
    @Column(name = "create_at")
    private LocalDateTime createAt;
    @Column(name = "update_at")
    private LocalDateTime updateAt;
}

package com.example.sportshopv2.Entity;

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
@Table(name="Bill_Detail")
public class hdct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "id_bill")
    private HoaDon hoaDon;

    @ManyToOne
    @JoinColumn(name = "id_product_detail")
    private SanPhamChiTiet sanPhamChiTiet;


    private Float price;

    private LocalDateTime create_at;
    private String create_by;
    private LocalDateTime update_at;
    private String update_by;
}

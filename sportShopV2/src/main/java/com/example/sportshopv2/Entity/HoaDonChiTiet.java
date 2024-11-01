package com.example.sportshopv2.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Bill_Detail")
public class HoaDonChiTiet {
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

    // Getters, setters, and other necessary annotations
}

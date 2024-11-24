package com.example.sportshopv2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_detail_promotion")
public class DotGiamGiaChiTiet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "status")
    private String status;

    @Column(name = "update_at")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime updateAt;

    @Column(name = "create_at")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createAt;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "deleted")
    private Boolean deleted;

    @ManyToOne
    @JoinColumn(name = "product_detail_id")
    private SanPhamChiTiet spct;

    @ManyToOne
    @JoinColumn(name = "promotion_id")
    private DotGiamGia dotGiamGia;

}

package com.example.sportshopv2.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "image")
public class AnhSanPham extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "id_product_detail")
    private Integer idSPCT;
    @Column(name = "name")
    private String tenAnh;
    @Column(name = "status")
    private String trangThai;
}

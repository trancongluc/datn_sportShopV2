package com.example.sportshopv2.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Product_detail")
public class SanPhamChiTiet extends BaseEntity{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "id_size")
    private Integer idKichThuoc;
    @Column(name = "id_product")
    private Integer idSanPham;
    @Column(name = "id_color")
    private Integer idMauSac;
    @Column(name = "id_brand")
    private Integer idThuongHieu;
    @Column(name = "id_sole")
    private Integer idDeGiay;
    @Column(name = "id_category")
    private Integer idTheLoai;
    @Column(name = "id_collar")
    private Integer idCoGiay;
    @Column(name = "id_material")
    private Integer idChatLieu;
    @Column(name = "description")
    private String moTa;
    @Column(name = "quantity")
    private Integer soLuong;
    @Column(name = "price")
    private Float gia;
    @Column(name = "status")
    private String trangThai;
    @Column(name = "gender")
    private String gioiTinh;




}

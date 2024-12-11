package com.example.sportshopv2.model;

import com.example.sportshopv2.repository.*;
import com.example.sportshopv2.dto.SanPhamChiTietDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Product_detail")
public class SPCT extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "id_size")
    private KichThuoc idKichThuoc;
    @ManyToOne
    @JoinColumn(name = "id_product")
    private SanPham idSanPham;
    @ManyToOne
    @JoinColumn(name = "id_color")
    private MauSac idMauSac;
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

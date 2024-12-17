package com.example.sportshopv2.dto;

import com.example.sportshopv2.model.*;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SanPhamChiTietDTO extends BaseEntity {

    private Integer id;

    private KichThuoc kichThuoc;

    private SanPham sanPham;

    private MauSac mauSac;

    private ThuongHieu thuongHieu;

    private DeGiay deGiay;
    private TheLoai theLoai;
    private CoGiay coGiay;
    private ChatLieu chatLieu;
    private String moTa;
    private Integer soLuong;
    private Float gia;
    private String trangThai;
    private String gioiTinh;
    private Float giaSauGiam;
    private List<AnhSanPham> anhSanPham;
    private List<HoaDonChiTiet> hoaDonChiTiet;
}

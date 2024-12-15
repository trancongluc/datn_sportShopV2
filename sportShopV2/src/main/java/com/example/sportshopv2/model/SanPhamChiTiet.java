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
public class SanPhamChiTiet extends BaseEntity {

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



    public static SanPhamChiTiet of(SanPhamChiTietDTO spctDTO) {
        return SanPhamChiTiet.builder()
                .id(spctDTO.getId())
                .idKichThuoc(spctDTO.getKichThuoc().getId())
                .idSanPham(spctDTO.getSanPham().getId())
                .idMauSac(spctDTO.getMauSac().getId())
                .idThuongHieu(spctDTO.getMauSac().getId())
                .idDeGiay(spctDTO.getDeGiay().getId())
                .idTheLoai(spctDTO.getTheLoai().getId())
                .idCoGiay(spctDTO.getTheLoai().getId())
                .idChatLieu(spctDTO.getChatLieu().getId())
                .moTa(spctDTO.getMoTa())
                .soLuong(spctDTO.getSoLuong())
                .gia(spctDTO.getGia())
                .trangThai(spctDTO.getTrangThai())
                .gioiTinh(spctDTO.getGioiTinh())
                .build();
    }

    public static SanPhamChiTietDTO toDTO(SanPhamChiTiet spct, KichThuocRepository kichThuocRepository,
                                          SanPhamRepository spRepo, MauSacRepository msRepo, ThuongHieuRepository thRepo,
                                          DeGiayRepository dgRepo, TheLoaiRepository tlRepo, CoGiayRepository cgRepo,
                                          ChatLieuRepository clRepo, AnhSanPhamRepository anhRepo) {
        List<AnhSanPham> danhSachAnh = anhRepo.findByIdSPCT(spct.getId()); // Giả sử có phương thức này
        return SanPhamChiTietDTO.builder()
                .id(spct.getId())
                .kichThuoc(kichThuocRepository.findById(spct.idKichThuoc).orElse(null)) // Tạo đối tượng KichThuoc từ ID
                .sanPham(spRepo.findById(spct.idSanPham).orElse(null)) // Tạo đối tượng SanPham từ ID
                .mauSac(msRepo.findById(spct.idMauSac).orElse(null)) // Tạo đối tượng MauSac từ ID
                .thuongHieu(thRepo.findById(spct.idThuongHieu).orElse(null)) // Tạo đối tượng ThuongHieu từ ID
                .deGiay(dgRepo.findById(spct.idDeGiay).orElse(null)) // Tạo đối tượng DeGiay từ ID
                .theLoai(tlRepo.findById(spct.idTheLoai).orElse(null)) // Tạo đối tượng TheLoai từ ID
                .coGiay(cgRepo.findById(spct.idCoGiay).orElse(null)) // Tạo đối tượng CoGiay từ ID
                .chatLieu(clRepo.findById(spct.idChatLieu).orElse(null)) // Tạo đối tượng ChatLieu từ ID
                .moTa(spct.getMoTa())
                .soLuong(spct.getSoLuong())
                .gia(spct.getGia())
                .trangThai(spct.getTrangThai())
                .gioiTinh(spct.getGioiTinh())
                .anhSanPham(danhSachAnh)
                .build();
    }
    public static SanPhamChiTietDTO toDTI(SanPhamChiTiet spct, HoaDonChiTietRepo hoaDonChiTietRepo){
        List<HoaDonChiTiet> danhSachHoaDonChiTiet = hoaDonChiTietRepo.findAllBySanPhamChiTiet_Id(spct.id);
        return SanPhamChiTietDTO.builder().id(spct.id).hoaDonChiTiet(danhSachHoaDonChiTiet).build();
    }

}

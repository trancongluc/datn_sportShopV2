package com.example.sportshopv2.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDTO {
    private String tenSanPham; // Tên sản phẩm
    private String anh; // Đường dẫn ảnh sản phẩm
    private String mauSac; // Màu sắc sản phẩm
    private int soLuong; // Số lượng sản phẩm
    private double giaBan; // Giá bán
    private double giaGoc; // Giá gốc
    private double tongTien; // Tổng tiền cho sản phẩm
    private String trangThai;
}

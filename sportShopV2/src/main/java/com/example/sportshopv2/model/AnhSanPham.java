package com.example.sportshopv2.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Image")
public class AnhSanPham {
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

    public byte[] getImageData() throws IOException {

        // Xây dựng đường dẫn đầy đủ đến file ảnh

        Path imagePath = Paths.get("D:\\demoMergeCodeDatn\\sportShopV2\\src\\main\\resources\\static\\images", tenAnh);

//        return Files.readAllBytes(imagePath);
        // Xây dựng đường dẫn đầy đủ đến file ảnh từ thư mục resources
//        Path imagePath = Paths.get("D:/DATN/sportShopV2/src/main/resources/static/images", tenAnh);


        // Kiểm tra xem file có tồn tại không
        if (Files.exists(imagePath)) {
            return Files.readAllBytes(imagePath);
        } else {
            throw new IOException("File ảnh không tồn tại: " + imagePath.toString());
        }
    }
}


package com.example.sportshopv2.service;

import com.example.sportshopv2.repository.AnhSanPhamRepository;
import com.example.sportshopv2.model.AnhSanPham;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnhService {
    private final AnhSanPhamRepository anhSanPhamRepository;

    public void saveImages(List<AnhSanPham> images) {
        if (images == null || images.isEmpty()) {
            throw new IllegalArgumentException("Danh sách ảnh không được null hoặc rỗng.");
        }

        String directory = "C:\\HOCTAP\\DATN\\sportShopV2\\sportShopV2\\src\\main\\resources\\static\\images"; // Đường dẫn đến thư mục lưu ảnh

        for (AnhSanPham image : images) {
            if (image == null) {
                System.out.println("Một đối tượng ảnh là null.");
                continue; // Bỏ qua ảnh này
            }

            try {
                byte[] imageData = image.getImageData(); // Lấy dữ liệu ảnh từ file
                String fileName = image.getTenAnh();

                if (imageData == null || fileName == null) {
                    System.out.println("Dữ liệu ảnh hoặc tên file là null.");
                    continue; // Bỏ qua ảnh này
                }

                Path filePath = Paths.get(directory, fileName);
                Files.write(filePath, imageData);
            } catch (IOException e) {
                e.printStackTrace(); // Xử lý lỗi
            }
        }

        // Lưu thông tin vào repository sau khi lưu ảnh
        anhSanPhamRepository.saveAll(images);
    }
    public AnhSanPham anhSanPhamByIDSPCT(Integer idSPCT){
        return anhSanPhamRepository.findByIdSPCT(idSPCT).get(0);

    }
}

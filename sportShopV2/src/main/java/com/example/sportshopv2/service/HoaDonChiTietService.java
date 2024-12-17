package com.example.sportshopv2.service;

import com.example.sportshopv2.model.HoaDon;
import com.example.sportshopv2.model.HoaDonChiTiet;
import com.example.sportshopv2.repository.HoaDonChiTietRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HoaDonChiTietService {
    private  final HoaDonChiTietRepo hdctRepo;
    @Transactional // Đảm bảo tất cả các thao tác trong phương thức này đều thực thi trong một giao dịch
    public void createBillDetails(List<HoaDonChiTiet> hoaDonChiTietList) {
        for (HoaDonChiTiet hoaDonChiTiet : hoaDonChiTietList) {
            // Kiểm tra nếu cần (ví dụ: validate dữ liệu hoặc tính toán giá trị)
            // Sau đó lưu vào cơ sở dữ liệu
            hoaDonChiTiet.setCreate_at(LocalDateTime.now());
            hoaDonChiTiet.setCreate_by("1");
            hdctRepo.save(hoaDonChiTiet);
        }
    }
    public HoaDonChiTiet updateHoaDonCT(Integer idHDCT, HoaDonChiTiet updatedHDCT) {
        // Tìm hóa đơn theo ID
        HoaDonChiTiet hdct = hdctRepo.findAllById(idHDCT);
        if (hdct != null) {;
            hdct.setQuantity(updatedHDCT.getQuantity());
            // Cập nhật metadata
            hdct.setUpdate_at(LocalDateTime.now());
            hdct.setUpdate_by("system"); // hoặc từ tài khoản hiện tại
            // Lưu lại thông tin cập nhật
           return   hdctRepo.save(hdct);
        } else {
            throw new EntityNotFoundException("Hóa đơn với mã " + idHDCT + " không tồn tại");
        }
    }
    public HoaDonChiTiet hdctDetail(Integer idHDCT){
       return hdctRepo.findAllById(idHDCT);
    }
}

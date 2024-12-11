package com.example.sportshopv2.service;

import com.example.sportshopv2.model.HoaDon;
import com.example.sportshopv2.model.HoaDonChiTiet;
import com.example.sportshopv2.repository.HoaDonChiTietRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

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
            hdctRepo.save(hoaDonChiTiet);
        }
    }
  /* @Transactional // Đảm bảo tất cả các thao tác trong phương thức này đều thực thi trong một giao dịch
   public HoaDonChiTiet createBillDetails(HoaDonChiTiet hoaDonChiTiet) {

         return   hdctRepo.save(hoaDonChiTiet);

   }*/
}

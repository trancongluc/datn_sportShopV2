package com.example.sportshopv2.service;

import com.example.sportshopv2.model.HoaDon;
import com.example.sportshopv2.repository.HoaDonRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HoaDonService {
    private final HoaDonRepo hdRepo;

    public HoaDon createHoaDon(HoaDon hoaDon) {

        return hdRepo.save(hoaDon);
    }

    public HoaDon updateHoaDon(Integer idHD, HoaDon hoaDon) {
        try {
            if (!hdRepo.existsById(idHD)) {
                throw new EntityNotFoundException("Hóa đơn không tồn tại với ID: " + idHD);
            }
            hoaDon.setId(idHD);
            return hdRepo.save(hoaDon);
        } catch (Exception e) {
            // Log lỗi chi tiết
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cập nhật hóa đơn không thành công", e);
        }
    }


    public HoaDon hoaDonById(Integer idHD) {

        return hdRepo.findById(idHD).orElse(null);
    }

    public List<HoaDon> getHoaDonTaiQuay() {
        List<HoaDon> listHD = hdRepo.findAllByStatus("Hóa Đơn Chờ");
        return listHD;
    }


    public List<HoaDon> getOrdersByCustomerId(Integer customerId) {
        return hdRepo.findByCustomerId(customerId);
    }

    public HoaDon findHoaDonById(Integer id) {
        return hdRepo.findById(id).orElse(null);
    }
}

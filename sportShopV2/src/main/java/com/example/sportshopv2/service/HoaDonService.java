package com.example.sportshopv2.service;

import com.example.sportshopv2.model.HoaDon;
import com.example.sportshopv2.repository.HoaDonRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HoaDonService {
    private final HoaDonRepo hdRepo;

    public HoaDon createHoaDon(HoaDon hoaDon) {

        return hdRepo.save(hoaDon);
    }

    public HoaDon updateHoaDon(Integer idHD, HoaDon hoaDon) {
        if (!hdRepo.existsById(idHD)) {
            throw new EntityNotFoundException("Hóa đơn không tồn tại với ID: " + idHD);
        }
        hoaDon.setId(idHD);
        return hdRepo.save(hoaDon);
    }

}

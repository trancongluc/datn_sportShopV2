package com.example.sportshopv2.service;

import com.example.sportshopv2.model.HoaDon;
import com.example.sportshopv2.repository.HoaDonRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HoaDonService {
    private final HoaDonRepo hdRepo;

    public HoaDon createHoaDon(HoaDon hoaDon) {
        hoaDon.setCreateAt(LocalDateTime.now());
        return hdRepo.save(hoaDon);
    }

    public HoaDon updateHoaDon(Integer idHD, HoaDon hoaDon) {
        try {
            if (!hdRepo.existsById(idHD)) {
                throw new EntityNotFoundException("Hóa đơn không tồn tại với ID: " + idHD);
            }
            hoaDon.setId(idHD);
            hoaDon.setCreateAt(LocalDateTime.now());
            hoaDon.setUpdateAt(LocalDateTime.now());
            if(hoaDon.getType().equals("Tại Quầy")){
                hoaDon.setReceive_date(LocalDateTime.now());
                hoaDon.setTransaction_date(LocalDateTime.now());
            }
            return hdRepo.save(hoaDon);
        } catch (Exception e) {
            // Log lỗi chi tiết
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cập nhật hóa đơn không thành công", e);
        }
    }
    public HoaDon updateHoaDonHoanTra(Integer idHD, HoaDon hoaDon) {
        try {
            if (!hdRepo.existsById(idHD)) {
                throw new EntityNotFoundException("Hóa đơn không tồn tại với ID: " + idHD);
            }
            hoaDon.setId(idHD);
            hoaDon.setUpdateAt(LocalDateTime.now());
            return hdRepo.save(hoaDon);
        } catch (Exception e) {
            // Log lỗi chi tiết
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cập nhật hóa đơn không thành công", e);
        }
    }

    public HoaDon hoaDonById(Integer idHD) {

        return hdRepo.findAllById(idHD);
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
    public int countByStatus(String status) {
        return hdRepo.countByStatus(status);
    }
    public int getTotalQuantityForCurrentMonth() {
        LocalDate now = LocalDate.now();
        int currentMonth = now.getMonthValue();
        int currentYear = now.getYear();

        return hdRepo.getTotalQuantityForMonthAndYear(currentMonth,currentYear);
    }
    public List<HoaDon> getBillsByCustomerId(Integer customerId) {
        return hdRepo.findByCustomerId(customerId);
    }

    public Page<HoaDon> getHoaDonsByStatus(String status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return hdRepo.findAllByStatusLikeOrderByCreateAtDesc(status, pageable);
    }

    public Page<HoaDon> getHoaDonsByStatusNot(String status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return hdRepo.findAllByStatusNotOrderByCreateAtDesc(status, pageable);
    }
}

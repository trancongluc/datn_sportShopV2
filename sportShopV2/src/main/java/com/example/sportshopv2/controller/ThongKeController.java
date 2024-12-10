package com.example.sportshopv2.controller;

import com.example.sportshopv2.repository.ThongKeRepository;
import com.example.sportshopv2.service.HoaDonService;
import com.example.sportshopv2.service.ThongKeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Controller

public class ThongKeController {

    @Autowired
    private ThongKeRepository billService;
    @Autowired
    private HoaDonService hoaDonService;
    @Autowired
    private ThongKeService thongKeService;

    @GetMapping("/statistical/view")
    public String viewStatistical(Model model) {

        // Lấy dữ liệu từ service
        Map<String, Object> stats = billService.getCompletedOrdersAndTotalMoney();
        Map<String, Object> statsToday = billService.getCompletedOrdersAndTotalMoneyToday();

        // Lấy tổng số đơn hàng (kiểm tra null và thay thế bằng 0 nếu cần)
        Long totalOrders = (stats.get("totalCompletedOrders") != null)
                ? ((Number) stats.get("totalCompletedOrders")).longValue()
                : 0L;

        // Lấy tổng số tiền và định dạng (kiểm tra null và thay thế bằng 0 nếu cần)
        BigDecimal totalMoney = (BigDecimal) stats.get("totalCompletedMoney");
        totalMoney = (totalMoney != null) ? totalMoney : BigDecimal.ZERO;
        DecimalFormat formatter = new DecimalFormat("#,###");
        String formattedMoney = formatter.format(totalMoney);

        // Lấy tổng số đơn hàng trong ngày hôm nay (kiểm tra null và thay thế bằng 0 nếu cần)
        Long totalOrdersTd = (statsToday.get("total_completed_orders_today") != null)
                ? ((Number) statsToday.get("total_completed_orders_today")).longValue()
                : 0L;

        // Lấy tổng số tiền trong ngày hôm nay và định dạng (kiểm tra null và thay thế bằng 0 nếu cần)
        BigDecimal totalMoneyTd = (BigDecimal) statsToday.get("total_completed_money_today");
        totalMoneyTd = (totalMoneyTd != null) ? totalMoneyTd : BigDecimal.ZERO;
        DecimalFormat formatter2 = new DecimalFormat("#,###");
        String formattedMoneyTd = formatter2.format(totalMoneyTd);
        Integer soLuongSPBanDuocTrongThang = hoaDonService.getTotalQuantityForCurrentMonth();
        // Đưa dữ liệu vào model
        model.addAttribute("totalOrders", totalOrders);
        model.addAttribute("totalMoney", formattedMoney);
        model.addAttribute("totalOrdersTd", totalOrdersTd);
        model.addAttribute("formattedMoneyTd", formattedMoneyTd);
        model.addAttribute("tongSPInMonth", soLuongSPBanDuocTrongThang);
        return "ThongKe/ThongKe";
    }

    @GetMapping("/thong-ke/thong-ke-nam")
    public ResponseEntity<?> getYearlyStatistics() {
        Map<String, Object> response = new HashMap<>();

        // Kiểm tra xem có dữ liệu từ service
        List<Integer> totalBills = thongKeService.getMonthlyBillCounts();
        List<Integer> totalProducts = thongKeService.getMonthlyProductCounts();

        // Debug: in ra dữ liệu để kiểm tra
        System.out.println("Bills: " + totalBills);
        System.out.println("Products: " + totalProducts);

        // Trả về response nếu dữ liệu hợp lệ
        response.put("totalBills", totalBills);
        response.put("totalProducts", totalProducts);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/thong-ke/thong-ke-thang")
    @ResponseBody
    public Map<String, List<Integer>> thongKeTheoThang(@RequestParam int month, @RequestParam int year) {
        List<Object[]> billData = thongKeService.getBillStatisticsByDay(month, year);
        List<Object[]> productData = thongKeService.getProductStatisticsByDay(month, year);

        List<Integer> totalBills = new ArrayList<>();
        List<Integer> totalProducts = new ArrayList<>();

        for (int day = 1; day <= 31; day++) {
            totalBills.add(0); // Mặc định là 0 nếu không có dữ liệu
            totalProducts.add(0);
        }

        for (Object[] bill : billData) {
            int day = (int) bill[0] - 1; // Chuyển ngày về chỉ số mảng
            totalBills.set(day, ((Long) bill[1]).intValue());
        }

        for (Object[] product : productData) {
            int day = (int) product[0] - 1; // Chuyển ngày về chỉ số mảng
            totalProducts.set(day, ((Long) product[1]).intValue());
        }

        Map<String, List<Integer>> result = new HashMap<>();
        result.put("totalBills", totalBills);
        result.put("totalProducts", totalProducts);
        return result;
    }

    @GetMapping("/thong-ke/thong-ke-ngay")
    @ResponseBody
    public Map<String, List<Integer>> thongKeTheoNgay(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return thongKeService.thongKeTheoNgay(date);
    }

    @GetMapping("/thong-ke/thong-ke-khoang-thoi-gian")
    @ResponseBody
    public ResponseEntity<?> getStatisticsByDateRange(@RequestParam("startDate")
                                                      LocalDateTime startDate,
                                                      @RequestParam("endDate")
                                                       LocalDateTime endDate) {
        Map<String, Object> response = new HashMap<>();

        // Kiểm tra xem có dữ liệu từ service
        List<Object[]> billStatisticsByDay = thongKeService.getBillStatisticsByDayInDateRange(startDate, endDate);
        List<Object[]> productStatisticsByDay = thongKeService.getProductStatisticsByDayInDateRange(startDate, endDate);

        // Tạo kết quả trả về
        response.put("billStatisticsByDay", billStatisticsByDay);
        response.put("productStatisticsByDay", productStatisticsByDay);

        return ResponseEntity.ok(response);
    }

}

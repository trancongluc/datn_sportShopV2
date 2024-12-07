package com.example.sportshopv2.controller;

import com.example.sportshopv2.repository.ThongKeRepository;
import com.example.sportshopv2.service.HoaDonService;
import com.example.sportshopv2.service.ThongKeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
        model.addAttribute("tongSPInMonth",soLuongSPBanDuocTrongThang);
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
    @GetMapping("/thong-ke/thong-ke-7-ngay")
    public ResponseEntity<?> thongKeTrong7Ngay() {
        Map<String, Object> response = new HashMap<>();

        // Lấy danh sách số lượng hóa đơn và sản phẩm trong 7 ngày
        List<Integer> totalBills = thongKeService.getLast7DaysBillCounts();  // Danh sách số lượng hóa đơn
        List<Integer> totalProducts = thongKeService.getLast7DaysProductCounts();  // Danh sách số lượng sản phẩm

        // Debug: In ra dữ liệu để kiểm tra
        System.out.println("Bills: " + totalBills);
        System.out.println("Products: " + totalProducts);

        // Kiểm tra xem dữ liệu có hợp lệ hay không (optional)
        if (totalBills.size() != 7 || totalProducts.size() != 7) {
            return ResponseEntity.badRequest().body("Dữ liệu không hợp lệ");
        }

        // Trả về dữ liệu cho mỗi ngày trong 7 ngày
        response.put("totalBills", totalBills);  // Số lượng hóa đơn theo 7 ngày
        response.put("totalProducts", totalProducts);  // Số lượng sản phẩm theo 7 ngày

        return ResponseEntity.ok(response);  // Trả về kết quả cho người dùng
    }
}

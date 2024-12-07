package com.example.sportshopv2.controller;

import com.example.sportshopv2.repository.ThongKeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

@Controller
public class ThongKeController {

    @Autowired
    private ThongKeRepository billService;

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

        // Đưa dữ liệu vào model
        model.addAttribute("totalOrders", totalOrders);
        model.addAttribute("totalMoney", formattedMoney);
        model.addAttribute("totalOrdersTd", totalOrdersTd);
        model.addAttribute("formattedMoneyTd", formattedMoneyTd);

        return "ThongKe/ThongKe";
    }

}

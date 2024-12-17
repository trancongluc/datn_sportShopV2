package com.example.sportshopv2.controller.ThanhToan;

import com.example.sportshopv2.config.VNPAYService;
import com.example.sportshopv2.model.TaiKhoan;
import com.example.sportshopv2.repository.TaiKhoanRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.Authenticator;
import java.util.Map;

import java.text.DecimalFormat;

@Controller
public class VNPayReturnController {
    @Autowired
    private VNPAYService vnPayService;
    @Autowired
    private TaiKhoanRepo taiKhoanRepo;

    @GetMapping("/vnpay-payment-return")
    public String paymentCompleted(HttpServletRequest request, Model model) {
        int paymentStatus = vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        DecimalFormat formatter = new DecimalFormat("#,###.##");
        String formattedTotalPrice = formatter.format(Double.parseDouble(totalPrice) / 100);

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", formattedTotalPrice + " VND");
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);

        Authentication authenticator = SecurityContextHolder.getContext().getAuthentication();
        String username = authenticator.getName();
        TaiKhoan taiKhoan = taiKhoanRepo.findTaiKhoanByUsername(username);
        if (taiKhoan.getRole().equals("Admin")) {
            return paymentStatus == 1 ? "BanHangTaiQuay/BanHangTaiQuay" : "Dathang/oderSuccess";
        }
        return paymentStatus == 1 ? "Dathang/oderSuccess" : "Dathang/orderFail";

    }
   /* @GetMapping("/vnpay-payment-return")
    @ResponseBody
    public ResponseEntity<?> paymentCompleted(HttpServletRequest request) {
        int paymentStatus = vnPayService.orderReturn(request);

        if (paymentStatus == 1) {
            // Thanh toán thành công
            return ResponseEntity.ok()
                    .body(Map.of("pay_status", 1, "message", "Thanh toán thành công", "redirectUrl", "/ban-hang-tai-quay"));
        } else {
            // Thanh toán thất bại
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("pay_status", 0, "message", "Thanh toán thất bại"));
        }
    }*/

}

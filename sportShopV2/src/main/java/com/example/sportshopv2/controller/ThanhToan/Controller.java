package com.example.sportshopv2.controller.ThanhToan;

import com.example.sportshopv2.config.VNPAYService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@org.springframework.stereotype.Controller
@RequestMapping("/VNPAY-demo")
public class Controller {
    @Autowired
    private VNPAYService vnPayService;

    @GetMapping({"", "/"})
    public String home() {
        return "Dathang/createOrder";
    }

    // Chuyển hướng người dùng đến cổng thanh toán VNPAY
    @PostMapping("/submitOrder")
    public String submidOrder(@RequestParam("amount") int orderTotal,
                              @RequestParam("orderInfo") String orderInfo,
                              HttpServletRequest request) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createOrder(request, orderTotal, orderInfo, baseUrl);
        return "redirect:" + vnpayUrl;
    }

    @GetMapping("/vnpay-payment-return")
    public String paymentCompleted(HttpServletRequest request, Model model) {
        try {
            // Lấy thông tin từ URL
            String orderInfo = request.getParameter("vnp_OrderInfo");
            String paymentTime = request.getParameter("vnp_PayDate");
            String transactionId = request.getParameter("vnp_TransactionNo");
            String totalPrice = request.getParameter("vnp_Amount");
            String responseCode = request.getParameter("vnp_ResponseCode");
            String transactionStatus = request.getParameter("vnp_TransactionStatus");



            // Kiểm tra mã phản hồi và trạng thái giao dịch
            int paymentStatus = (responseCode != null && responseCode.equals("00")) && (transactionStatus != null && transactionStatus.equals("00")) ? 1 : 0;

            if (paymentStatus == 1) {
                // Thanh toán thành công

                model.addAttribute("orderId", orderInfo);
                model.addAttribute("totalPrice", totalPrice);
                model.addAttribute("paymentTime", paymentTime);
                model.addAttribute("transactionId", transactionId);
                return "BanHangTaiQuay/BanHangTaiQuay";
            } else {
                // Thanh toán thất bại

                model.addAttribute("errorMessage", "Thanh toán thất bại!");
                return "Dathang/orderFail";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Đã xảy ra lỗi trong quá trình xử lý.");
            return "Dathang/orderFail";
        }
    }




}

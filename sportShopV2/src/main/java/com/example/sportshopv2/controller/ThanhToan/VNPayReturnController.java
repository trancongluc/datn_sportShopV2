package com.example.sportshopv2.controller.ThanhToan;

import com.example.sportshopv2.config.VNPAYService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class VNPayReturnController {
    @Autowired
    private VNPAYService vnPayService;

    /*@GetMapping("/vnpay-payment-return")
    public String paymentCompleted(HttpServletRequest request, Model model){
        int paymentStatus =vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);

        return paymentStatus == 1 ? "Dathang/oderSuccess" : "Dathang/orderFail";
    }*/
    @GetMapping("/vnpay-payment-return")
    @ResponseBody // Trả JSON thay vì view
    public ResponseEntity<?> paymentCompleted(HttpServletRequest request) {
        int paymentStatus = vnPayService.orderReturn(request);

        if (paymentStatus == 1) {
            return ResponseEntity.ok().body(Map.of("pay_status", 1, "message", "Thanh toán thành công"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("pay_status", 0, "message", "Thanh toán thất bại"));
        }
    }

}

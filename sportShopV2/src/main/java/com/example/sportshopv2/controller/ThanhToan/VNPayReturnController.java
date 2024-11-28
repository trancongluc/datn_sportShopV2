package com.example.sportshopv2.controller.ThanhToan;

import com.example.sportshopv2.config.VNPAYService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.DecimalFormat;

@Controller
public class VNPayReturnController {
    @Autowired
    private VNPAYService vnPayService;


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

        return paymentStatus == 1 ? "Dathang/oderSuccess" : "Dathang/orderFail";
    }

}

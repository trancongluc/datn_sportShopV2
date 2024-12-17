package com.example.sportshopv2.controller.ThanhToan;

import com.example.sportshopv2.config.VNPAYService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@org.springframework.stereotype.Controller
@RequestMapping("/VNPay/demo")
public class Controller {
    @Autowired
    private VNPAYService vnPayService;

    @GetMapping({"", "/"})
    public String home(){
        return "Dathang/createOrder";
    }

    // Chuyển hướng người dùng đến cổng thanh toán VNPAY
    @GetMapping("/submitOrder")
    @ResponseBody
    public String submidOrder(@RequestParam("amount") String orderTotal,
                              @RequestParam("orderInfo") String orderInfo,
                              HttpServletRequest request){
        System.out.println(orderTotal);
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createOrder(request, orderTotal, orderInfo, baseUrl);
        return vnpayUrl;
    }

}

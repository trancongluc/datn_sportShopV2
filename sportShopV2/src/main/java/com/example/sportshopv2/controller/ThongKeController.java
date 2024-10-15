package com.example.sportshopv2.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ThongKeController {
    @GetMapping("/statistical/view")
    public String viewStatistical(){
        return "ThongKe/ThongKe";
    }
}

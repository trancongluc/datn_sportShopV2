package com.example.sportshopv2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HoaDonController {
    @GetMapping("/bill/view")
    public String view(Model model) {
//        model.addAttribute("messager", "Hello");
        return "HoaDon/HoaDon";
    }
}

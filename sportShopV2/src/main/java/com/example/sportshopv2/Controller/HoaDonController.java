package com.example.sportshopv2.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HoaDonController {
    @RequestMapping("/bill/view")
    public String view(Model model){
        model.addAttribute("messager", "Hello");
        return "view/HoaDon";
    }
}

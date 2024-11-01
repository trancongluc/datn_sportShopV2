package com.example.sportshopv2.controller;

import com.example.sportshopv2.Service.NhanVienService;
import com.example.sportshopv2.entity.Address;
import com.example.sportshopv2.entity.User;
import com.example.sportshopv2.repository.NhanVienRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class NhanVienController {
    @Autowired
    NhanVienRepo nvrp;

    @Autowired
    NhanVienService sv;

    @GetMapping("/quan-ly-nhan-vien")
    public String GetAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(value = "keyword", required = false) String keyword,
            Model model) {
//        Page<User> nv = nvrp.findAllEmp(PageRequest.of(page, size));
        Pageable pageable = PageRequest.of(page, size);
        Page<User> nv;

        if (keyword != null && !keyword.isEmpty()) {
            nv = sv.searchEmp(keyword, pageable);
        } else {
            nv = sv.getAllDesc(pageable);
        }

        model.addAttribute("khachHang", new User());
        model.addAttribute("emp", nv.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", nv.getTotalPages());
        model.addAttribute("keyword", keyword);

        return "NhanVien/QL-nhan-vien";
    }

    @GetMapping("/them-nhan-vien")
    public String getinterface() {
        return "NhanVien/nhan-vien-add";
    }

    @PostMapping("/add")
    public String add(@RequestParam("fullName") String fullName,
                      @RequestParam("phoneNumber") String phoneNumber,
                      @RequestParam("email") String email,
                      @RequestParam("gender") String gender,
                      @RequestParam("date") String date,
                      @RequestParam("imageFile") MultipartFile imageFile,
                      @RequestParam("tinh_name") String tinh,
                      @RequestParam("quan_name") String quan,
                      @RequestParam("phuong_name") String phuong,
                      @RequestParam("line") String line,
                      @RequestParam("username") String username,
                      @RequestParam("password") String password,
                      @RequestParam("role") String role,
                      Model model) {

        User nvi = new User();
        nvi.setFullName(fullName);
        nvi.setPhoneNumber(phoneNumber);
        nvi.setEmail(email);
        nvi.setGender(gender);
        nvi.setDate(date);

        // Thêm địa chỉ
        Address address = new Address();
        address.setTinh(tinh);
        address.setQuan(quan);
        address.setPhuong(phuong);
        address.setLine(line);
        // Thiết lập các thông tin ID nếu cần
        // address.setProvince_id(...);
        // address.setDistrict_id(...);
        // address.setWard_id(...);

        nvi.addAddress(address); // Thêm địa chỉ vào khách hàng


        try {
            sv.addnv(nvi, imageFile, role, username, password);
            model.addAttribute("successMessage", "NV đã được thêm thành công!");
        } catch (IOException e) {
            model.addAttribute("errorMessage", "Tải lên hình ảnh không thành công.");
        }
//
// Chuyển hướng đến trang hiển thị sau khi thêm
        return "redirect:/quan-ly-nhan-vien";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmp(@PathVariable("id") Integer id) {
        sv.deleteEmpById(id); // Call the service to delete the customer
        return "redirect:/quan-ly-nhan-vien"; // Redirect to the display page after deletion
    }

    //
    @GetMapping("/emp/detail/{id}")
    public String viewDetails(@PathVariable("id") Integer id, Model model) {
        User emp = sv.getEmpById(id); // Add this method to UserService
        model.addAttribute("employ", emp);

        return "NhanVien/detail"; // Create a new Thymeleaf template for details
    }

    //
    @PostMapping("/emp/update/{id}")
    public String updateCustomer(@PathVariable("id") Integer id,
                                 @RequestParam("fullName") String fullName,
                                 @RequestParam("phoneNumber") String phoneNumber,
                                 @RequestParam("email") String email,
                                 @RequestParam("gender") String gender,
                                 @RequestParam("date") String date,
                                 @RequestParam("imageFile") MultipartFile imageFile,
                                 @RequestParam("tinh_name") String tinh,
                                 @RequestParam("quan_name") String quan,
                                 @RequestParam("phuong_name") String phuong,
                                 @RequestParam("line") String line,
                                 Model model) {
        try {
            User empl = sv.getEmpById(id); // Fetch existing customer
            empl.setFullName(fullName);
            empl.setPhoneNumber(phoneNumber);
            empl.setEmail(email);
            empl.setGender(gender);
            empl.setDate(date);

            // Update address
            Address address = empl.getAddresses().get(0); // Assuming only one address
            address.setTinh(tinh);
            address.setQuan(quan);
            address.setPhuong(phuong);
            address.setLine(line);

            // Update image if new file is uploaded
            if (!imageFile.isEmpty()) {
                sv.updateEmpImage(empl, imageFile); // Add this method to UserService
            }

            sv.updateNhanVien(empl); // Create this method to save the updated customer
            model.addAttribute("successMessage", "TT nhân viên đã được cập nhật thành công!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Cập nhật không thành công.");
        }
        return "redirect:/quan-ly-nhan-vien"; // Redirect back to the customer list
    }
}


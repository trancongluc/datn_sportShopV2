package com.example.sportshopv2.controller.KhachHang;

import com.example.sportshopv2.Service.KhachhangService;
import com.example.sportshopv2.entity.Address;

import com.example.sportshopv2.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class khachhangController {



    // Hiển thị danh sách khách hàng
//    @GetMapping("/hien-thi")
//    public String listKhachHang(Model model) {
//        List<KhachHang> khachHangs = khachHangService.findAll();
//        model.addAttribute("khachHangs", khachHangs);
//        return "KhachHang/khachhang"; // trả về tên view để hiển thị
//    }
//
//    @GetMapping("/hien-thi")
//    public String getKhachHangs(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "3") int size,
//            @RequestParam(required = false) String keyword,  // New search parameter
//            Model model) {
//
//        Pageable pageable = PageRequest.of(page, size);
//        Page<User> khachHangPage;
//
//        // Check if a search keyword is provided
//        if (keyword != null && !keyword.isEmpty()) {
//            khachHangPage = khachHangService.searchKhachHang(keyword, pageable);
//        } else {
//            khachHangPage = khachHangService.findAll(pageable);
//        }
//
//        model.addAttribute("khachHang", new User());
//        model.addAttribute("khachHangs", khachHangPage.getContent());
//        model.addAttribute("currentPage", khachHangPage.getNumber());
//        model.addAttribute("totalPages", khachHangPage.getTotalPages());
//        model.addAttribute("keyword", keyword);  // Preserve keyword in the search input
//
//        return "KhachHang/khachhang";
//    }

    @Autowired
    private KhachhangService userService;

//
//    @GetMapping("/khach-hang/list")
//    public String displayCustomers(@RequestParam(defaultValue = "0") int page,
//                                   @RequestParam(defaultValue = "5") int size,
//
//                                   Model model) {
//        Page<User> customers = userService.getAllCustomers(PageRequest.of(page, size));
//
//
//        model.addAttribute("khachHang", new User());
//        model.addAttribute("customers", customers.getContent());
//        model.addAttribute("currentPage", page);
//        model.addAttribute("totalPages", customers.getTotalPages());
//
//        return "KhachHang/khachhang";
//    }

    @GetMapping("/khach-hang/list")
    public String displayCustomers(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "5") int size,
                                   @RequestParam(value = "keyword", required = false) String keyword,
                                   Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> customers;

        if (keyword != null && !keyword.isEmpty()) {
            customers = userService.searchCustomers(keyword, pageable);
        } else {
            customers = userService.getAllCustomers(pageable);
        }

        model.addAttribute("khachHang", new User());
        model.addAttribute("customers", customers.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", customers.getTotalPages());
        model.addAttribute("keyword", keyword); // Để giữ từ khóa tìm kiếm trong ô tìm kiếm

        return "KhachHang/khachhang";
    }
    @GetMapping("/add")
    public String addCustomer(Model model) {
        model.addAttribute("user", new User());
        return "KhachHang/tao-khach-hang";
    }


    @PostMapping("/add-khach-hang")
    public String addKhachHang(@RequestParam("fullName") String fullName,
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
        User khachHang = new User();
        khachHang.setFullName(fullName);
        khachHang.setPhoneNumber(phoneNumber);
        khachHang.setEmail(email);
        khachHang.setGender(gender);
        khachHang.setDate(date);

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

        khachHang.addAddress(address); // Thêm địa chỉ vào khách hàng



        try {
            userService.addKhachHang(khachHang, imageFile);
            model.addAttribute("successMessage", "Khách hàng đã được thêm thành công!");
        } catch (IOException e) {
            model.addAttribute("errorMessage", "Tải lên hình ảnh không thành công.");
        }

        return "redirect:/khach-hang/list"; // Chuyển hướng đến trang hiển thị sau khi thêm
    }
    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable("id") Integer id) {
        userService.deleteCustomerById(id); // Call the service to delete the customer
        return "redirect:/khach-hang/list"; // Redirect to the display page after deletion
    }

    @GetMapping("/customer/detail/{id}")
    public String viewCustomerDetails(@PathVariable("id") Integer id, Model model) {
        User customer = userService.getCustomerById(id); // Add this method to UserService
        model.addAttribute("customer", customer);

        return "KhachHang/khachhangdetail"; // Create a new Thymeleaf template for details
    }

    @PostMapping("/customer/update/{id}")
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
            User customer = userService.getCustomerById(id); // Fetch existing customer
            customer.setFullName(fullName);
            customer.setPhoneNumber(phoneNumber);
            customer.setEmail(email);
            customer.setGender(gender);
            customer.setDate(date);

            // Update address
            Address address = customer.getAddresses().get(0); // Assuming only one address
            address.setTinh(tinh);
            address.setQuan(quan);
            address.setPhuong(phuong);
            address.setLine(line);

            // Update image if new file is uploaded
            if (!imageFile.isEmpty()) {
                userService.updateCustomerImage(customer, imageFile); // Add this method to UserService
            }

            userService.updateKhachHang(customer); // Create this method to save the updated customer
            model.addAttribute("successMessage", "Khách hàng đã được cập nhật thành công!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Cập nhật không thành công.");
        }
        return "redirect:/khach-hang/list"; // Redirect back to the customer list
    }






}
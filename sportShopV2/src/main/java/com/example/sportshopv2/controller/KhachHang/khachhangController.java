package com.example.sportshopv2.controller.KhachHang;

import com.example.sportshopv2.Repository.AddressRepository;
import com.example.sportshopv2.Repository.KhachHangRepository;
import com.example.sportshopv2.service.AddressService;
import com.example.sportshopv2.service.KhachhangService;
import com.example.sportshopv2.entity.Address;

import com.example.sportshopv2.entity.User;
import jakarta.servlet.http.HttpSession;
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
@RequestMapping("/khach-hang")

public class khachhangController {



    @Autowired
    private KhachhangService userService;
    @Autowired
    private KhachHangRepository khachHangRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AddressService addressService;


    @GetMapping("/list")
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
    public String addKhachHang(
                                @RequestParam("code") String code,
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
//                               @RequestParam("username") String username,
//                               @RequestParam("password") String password,
                               Model model) {


        User khachHang = new User();
        khachHang.setCode(code);
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

    @GetMapping("/customer/detaill/{id}")
    public String viewCustomerDetails(@PathVariable("id") Integer id, Model model,HttpSession session) {
        User customer = userService.getCustomerById(id); // Add this method to UserService
        model.addAttribute("customer", customer);



        return "KhachHang/khachhangdetail"; // Create a new Thymeleaf template for details
    }

    @GetMapping("/customer/diachi/{id}")
    public String viewdiachi(@PathVariable("id") Integer id, Model model) {
        User customer = userService.getCustomerById(id); // Add this method to UserService
        model.addAttribute("customer", customer);

        return "KhachHang/diachi"; // Create a new Thymeleaf template for details
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

    @PostMapping("/addAddress/{id}")
    public String addAddress(@PathVariable("id") Integer id,
                             @RequestParam("tinh_name") String tinh,
                             @RequestParam("quan_name") String quan,
                             @RequestParam("phuong_name") String phuong,
                             @RequestParam("line") String line,
                             Model model) {

        // Tìm khách hàng theo id
        User customer = userService.findById(id);

        if (customer != null) {
            // Tạo đối tượng Address mới và thêm vào khách hàng
            Address newAddress = new Address();
            newAddress.setTinh(tinh);
            newAddress.setQuan(quan);
            newAddress.setPhuong(phuong);
            newAddress.setLine(line);


            // Thêm địa chỉ vào khách hàng
            customer.addAddress(newAddress);

            // Lưu lại thay đổi
            userService.save(customer);
        }

        // Hiển thị lại thông tin khách hàng và danh sách địa chỉ
        model.addAttribute("customer", customer);
        return "KhachHang/diachi"; // Tên view để hiển thị lại chi tiết khách hàng
    }
    @GetMapping("/customer/delete-address/{customerId}/{addressId}")
    public String deleteAddress(@PathVariable("customerId") Integer customerId,
                                @PathVariable("addressId") Integer addressId,
                                Model model) {

        // Find the customer by ID
        User customer = userService.findById(customerId);

        if (customer != null) {
            // Find the address to delete by ID
            Address addressToDelete = addressRepository.findById(addressId).orElse(null);

            if (addressToDelete != null) {
                // Remove the address from the customer's address list
                customer.getAddresses().remove(addressToDelete);

                // Save the updated customer
                userService.save(customer);
            }
        }

        // Add the updated customer to the model and return to the customer's address page
        model.addAttribute("customer", customer);
        return "KhachHang/diachi"; // Return to the page displaying the customer's addresses
    }


    }



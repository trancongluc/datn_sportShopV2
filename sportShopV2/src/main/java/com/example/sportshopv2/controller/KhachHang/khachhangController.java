package com.example.sportshopv2.controller.KhachHang;

import com.example.sportshopv2.dto.UserDTO;
import com.example.sportshopv2.model.Address;
import com.example.sportshopv2.model.User;
import com.example.sportshopv2.repository.AddressRepository;
import com.example.sportshopv2.repository.KhachHangRepository;
import com.example.sportshopv2.service.AddressService;
import com.example.sportshopv2.service.KhachhangService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;


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
            @ModelAttribute("customer")  User customer,
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

//        khachHang.setCode(code);

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

        // Kiểm tra trùng số điện thoại và email
        Optional<User> existingKhachHangByPhone = userService.findByPhoneNumber(khachHang.getPhoneNumber());
        Optional<User> existingKhachHangByEmail = userService.findByEmail(khachHang.getEmail());

        if (existingKhachHangByPhone.isPresent() || existingKhachHangByEmail.isPresent()) {
            if (existingKhachHangByPhone.isPresent()) {
                model.addAttribute("phoneError", "Số điện thoại đã tồn tại.");
            }
            if (existingKhachHangByEmail.isPresent()) {
                model.addAttribute("emailError", "Email đã tồn tại.");
            }
            return "KhachHang/tao-khach-hang"; // Trả lại trang form với thông báo lỗi
        }
        if (!phoneNumber.matches("^[0-9]{10,15}$")) {
            model.addAttribute("errorMessage", "Số điện thoại phải có từ 10 đến 15 chữ số.");
            return "KhachHang/tao-khach-hang";
        }
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
    public String viewCustomerDetails(@PathVariable("id") Integer id, Model model,HttpSession session) {
        User customer = userService.getCustomerById(id); // Add this method to UserService
        model.addAttribute("customer", customer);



        return "KhachHang/khachhangdetail"; // Create a new Thymeleaf template for details
    }

    @GetMapping("/thong-tin-kh/{idKH}")
    @ResponseBody
    public User thongTinKH(@PathVariable("idKH") Integer id) {
        UserDTO userKHDTO =  userService.getKHById(id);
        User user = User.of(userKHDTO);
        return user;
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
//                                 @RequestParam("tinh_name") String tinh,
//                                 @RequestParam("quan_name") String quan,
//                                 @RequestParam("phuong_name") String phuong,
//                                 @RequestParam("line") String line,

                                 Model model) {
        try {
            User customer = userService.getCustomerById(id); // Fetch existing customer

            // Kiểm tra trùng số điện thoại và email
            Optional<User> existingCustomerByPhone = userService.findByPhoneNumber(phoneNumber);
            Optional<User> existingCustomerByEmail = userService.findByEmail(email);

            if (existingCustomerByPhone.isPresent() && !existingCustomerByPhone.get().getId().equals(id)) {
                model.addAttribute("phoneError", "Số điện thoại đã tồn tại.");
            }
            if (existingCustomerByEmail.isPresent() && !existingCustomerByEmail.get().getId().equals(id)) {
                model.addAttribute("emailError", "Email đã tồn tại.");
            }

            // Nếu có lỗi, trả về trang chỉnh sửa
            if (model.containsAttribute("phoneError") || model.containsAttribute("emailError")) {
                model.addAttribute("customer", customer); // Truyền dữ liệu khách hàng hiện tại vào form
                return "KhachHang/khachhangdetail"; // Tên trang chỉnh sửa khách hàng
            }

            customer.setFullName(fullName);
            customer.setPhoneNumber(phoneNumber);
            customer.setEmail(email);
            customer.setGender(gender);
            customer.setDate(date);

//            // Update address
//            Address address = customer.getAddresses().get(0); // Assuming only one address
//            address.setTinh(tinh);
//            address.setQuan(quan);
//            address.setPhuong(phuong);
//            address.setLine(line);


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

    @PostMapping("/customer/update-address/{customerId}")
    public String updateAddress(@PathVariable("customerId") Integer customerId,
                                @RequestParam("addressId") Integer addressId,
                                @RequestParam("tinh") String tinh,
                                @RequestParam("tinhName") String tinhName,
                                @RequestParam("quan") String quan,
                                @RequestParam("quanName") String quanName,
                                @RequestParam("phuong") String phuong,
                                @RequestParam("phuongName") String phuongName,
                                @RequestParam("line") String line) {
        // Update the address in the database with the provided details
        addressService.updateAddress(customerId, addressId, tinhName, quanName, phuongName, line);

        // Redirect back to the address view page
        return "redirect:/khach-hang/customer/diachi/" + customerId;
    }


    @GetMapping("/customer/select-address/{customerId}/{addressId}")
    public String selectAddress(@PathVariable("customerId") Integer customerId, @PathVariable("addressId") Integer addressId, HttpSession session) {
        // Lấy khách hàng và địa chỉ theo ID
        User customer = userService.findCustomerById(customerId);
        if (customer == null || customer.getAddresses() == null || customer.getAddresses().isEmpty()) {
            return "redirect:/khach-hang/list";
        }
        Address selectedAddress = userService.findAddressById(addressId);
        if (selectedAddress != null) {
            // Lưu địa chỉ đã chọn vào session
            session.setAttribute("selectedAddress_" + customerId, selectedAddress);
        }
        // Chuyển hướng đến trang danh sách khách hàng
        return "redirect:/khach-hang/list";
    }


}




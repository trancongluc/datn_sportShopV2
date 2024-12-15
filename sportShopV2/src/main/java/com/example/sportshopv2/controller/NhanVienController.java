package com.example.sportshopv2.controller;

import com.example.sportshopv2.dto.HistoryDTO;
import com.example.sportshopv2.dto.UserDTO;
import com.example.sportshopv2.dto.UserNhanVienDTO;
import com.example.sportshopv2.model.Account;
import com.example.sportshopv2.model.Address;
import com.example.sportshopv2.model.HoaDon;
import com.example.sportshopv2.model.User;
import com.example.sportshopv2.repository.AccountRepo;
import com.example.sportshopv2.repository.HoaDonRepo;
import com.example.sportshopv2.repository.NhanVienRepo;
import com.example.sportshopv2.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

@Controller
@RequestMapping("/nhanvien")
public class NhanVienController {
    @Autowired
    NhanVienRepo nvrp;

    @Autowired
    NhanVienService sv;

    @Autowired
    AccountRepo acc;

    @Autowired
    HoaDonRepo hd;

    String password = "";
    String sendPassword = "";

    @GetMapping("/list")
    public String getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(value = "keyword", required = false) String keyword,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<User> users;

        // Kiểm tra từ khóa tìm kiếm
        if (keyword != null && !keyword.isEmpty()) {
            users = sv.searchEmp(keyword, pageable);
        } else {
            users = sv.getAllDesc(pageable);
        }

        // Thêm dữ liệu vào model

        model.addAttribute("emp", users.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", users.getTotalPages());
        model.addAttribute("keyword", keyword);

        return "NhanVien/QL-nhan-vien"; // Đảm bảo đường dẫn chính xác
    }

    @GetMapping("/them-nhan-vien")
    public String getinterface(Model model, String matKhau) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        int length = 10;

        Random rand = new Random();

        for (int i = 0; i < length; i++) {
            int index = rand.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            password += randomChar;
        }
        sendPassword = password;
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        matKhau = ("{bcrypt}" + encoder.encode(password));
        model.addAttribute("pass", matKhau);
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

        sv.mailSend(email, username, sendPassword);
        User nvi = new User();
        nvi.setCode("NV");
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
        address.setProvince_id(1);
        address.setDistrict_id(2);
        address.setWard_id(3);

        nvi.addAddress(address); // Thêm địa chỉ vào khách hàng
        try {
            sv.addnv(nvi, imageFile, role, username, password);
            model.addAttribute("successMessage", "NV đã được thêm thành công!");
        } catch (IOException e) {
            model.addAttribute("errorMessage", "Tải lên hình ảnh không thành công.");
        }


// Chuyển hướng đến trang hiển thị sau khi thêm
        return "redirect:/nhanvien/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmp(@PathVariable("id") Integer id) {
        sv.deleteEmpById(id); // Call the service to delete the customer
        return "redirect:/NhanVien/QL-nhan-vien"; // Redirect to the display page after deletion
    }

    //
    @GetMapping("/detail/{id}")
    public String viewDetails(@PathVariable("id") Integer id, Model model) {
        User emp = sv.getEmpById(id); // Add this method to UserService
        model.addAttribute("employ", emp);

        return "NhanVien/detail"; // Create a new Thymeleaf template for details
    }

    @GetMapping("/thong-tin-nv/{idNV}")
    @ResponseBody
    public User thongTinNV(@PathVariable("idNV") Integer id) {
        UserNhanVienDTO userNVDTO = sv.getNVById(id);
        User user = User.of(userNVDTO);
        return user;
    }

    @GetMapping("/order_history/{id}")
    public String viewHistory(@PathVariable("id") Integer id, Model model) {
        User emp = sv.getEmpById(id); // Add this method to UserService
        model.addAttribute("employ", emp);
        Account ac = acc.getAccByIdUser(id);
        System.out.println(ac.getId());
        List<Object[]> results = hd.getHistory(ac.getId());
        List<HistoryDTO> historyList = new ArrayList<>();
        for (Object[] result : results) {
            // Định dạng total_money thành VND
            NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            String formattedTotalMoney = formatter.format(((BigDecimal) result[4]).doubleValue());

            // Tạo HistoryDTO
            HistoryDTO dto = new HistoryDTO(
                    (Integer) result[0], // id
                    (String) result[1], // user_name
                    (Date) result[2], // create_at
                    (String) result[3], // status
                    formattedTotalMoney, // formatted total_money
                    (String) result[5], // address
                    (String) result[6]  // tenSanPham
            );

            historyList.add(dto);
        }

        model.addAttribute("his", historyList);
        return "NhanVien/order_history"; // Create a new Thymeleaf template for details
    }


    @PostMapping("/update/{id}")
    public String updateStaff(@PathVariable("id") Integer id,
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
        return "redirect:/nhanvien/list"; // Redirect back to the customer list
    }


}




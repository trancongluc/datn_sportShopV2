package com.example.sportshopv2.controller;

import com.example.sportshopv2.Service.NhanVienService;
import com.example.sportshopv2.entity.Address;
import com.example.sportshopv2.entity.User;
import com.example.sportshopv2.repository.NhanVienRepo;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import org.aspectj.bridge.Message;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.PasswordAuthentication;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

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

        Pageable pageable = PageRequest.of(page, size);
        Page<User> nv;

        if (keyword != null && !keyword.isEmpty()) {
            nv = sv.searchEmp(keyword, pageable);
        } else {
            nv = sv.getAllDesc(pageable);
        }

//
        model.addAttribute("emp", nv.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", nv.getTotalPages());
        model.addAttribute("keyword", keyword);

        return "NhanVien/QL-nhan-vien";
    }


    @GetMapping("/api/qr-result")
  public @ResponseBody  String receiveQrResult(@RequestBody Map<String, String> payload, Model model) {
        // Lấy kết quả QR từ payload
        String qrResult = payload.get("qrResult");

        // Xử lý kết quả mã QR tại đây (ví dụ: lưu vào cơ sở dữ liệu, thực hiện kiểm tra,...)
        System.out.println("Kết quả mã QR nhận được: " + qrResult);

        // Trả về phản hồi cho client
        //return "Kết quả đã được nhận thành công!";

        //Autofill
        if (qrResult.equals("")) {
            System.out.println("empty!");
        } else {
            String[] parts = qrResult.split("\\|");

            if (parts.length >= 7) {
                String id = parts[0];
                String name = parts[2];
                String bd = parts[3];
                String gender = parts[4];
                String address = parts[5];
                //              String date = parts[6];

                String[] parts2 = address.split(",");

                if (parts2.length >= 4) {
                    String part1 = parts2[0].trim();
                    String part2 = parts2[1].trim();
                    String part3 = parts2[2].trim();
                    String part4 = parts2[3].trim();

                    model.addAttribute("d_address", part1);
                    model.addAttribute("ward", part2);
                    model.addAttribute("district", part3);
                    model.addAttribute("city", part4);

                }


                // Định dạng chuỗi ban đầu
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("ddMMyyyy");

                // Chuyển chuỗi thành LocalDate
                LocalDate bdate = LocalDate.parse(bd, inputFormatter);

                // Định dạng lại theo kiểu MM/dd/yyyy
                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

                // In kết quả
                String formattedDate = bdate.format(outputFormatter);
                System.out.println(formattedDate);

                model.addAttribute("id", id);
                model.addAttribute("name", name);
                model.addAttribute("date", formattedDate);
                model.addAttribute("gender", gender);

            } else {
                // Xử lý trường hợp mảng parts không chứa đủ các phần tử cần thiết
            }
        }
        return "";
    }

    @GetMapping("/them-nhan-vien")
    public String getinterface() {
        return "NhanVien/nhan-vien-add";
    }


    @PostMapping("/add")
    public @ResponseBody String add(@RequestParam("fullName") String fullName,
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
                      Model model, @RequestBody Map<String, String> payload) {
        // Lấy kết quả QR từ payload
        String qrResult = payload.get("qrResult");

        // Xử lý kết quả mã QR tại đây (ví dụ: lưu vào cơ sở dữ liệu, thực hiện kiểm tra,...)
        System.out.println("Kết quả mã QR nhận được: " + qrResult);

        // Trả về phản hồi cho client
        //return "Kết quả đã được nhận thành công!";

        //Autofill
        if (qrResult.equals("")) {
            System.out.println("empty!");
        } else {
            String[] parts = qrResult.split("\\|");

            if (parts.length >= 7) {
                String id = parts[0];
                String name = parts[2];
                String bd = parts[3];
                String sgender = parts[4];
                String saddress = parts[5];

                String[] parts2 = saddress.split(",");

                if (parts2.length >= 4) {
                    String part1 = parts2[0].trim();
                    String part2 = parts2[1].trim();
                    String part3 = parts2[2].trim();
                    String part4 = parts2[3].trim();

                    model.addAttribute("d_address", part1);
                    model.addAttribute("ward", part2);
                    model.addAttribute("district", part3);
                    model.addAttribute("city", part4);

                }


                // Định dạng chuỗi ban đầu
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("ddMMyyyy");

                // Chuyển chuỗi thành LocalDate
                LocalDate bdate = LocalDate.parse(bd, inputFormatter);

                // Định dạng lại theo kiểu MM/dd/yyyy
                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

                // In kết quả
                String formattedDate = bdate.format(outputFormatter);
                System.out.println(formattedDate);

                model.addAttribute("id", id);
                model.addAttribute("name", name);
                model.addAttribute("date", formattedDate);
                model.addAttribute("gender", sgender);

            } else {
                // Xử lý trường hợp mảng parts không chứa đủ các phần tử cần thiết
            }
        }
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

//    public void emailSend() {
//        String passwd = "";
//        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
//      //  String password = "";
//        int length = 8;
//
//        Random rand = new Random();
//
//        for (int i = 0; i < length; i++) {
//            int index = rand.nextInt(characters.length());
//            char randomChar = characters.charAt(index);
//            passwd += randomChar;
//        }
//        try {
//            Properties props = new Properties();
//            props.put("mail.smtp.auth", "true");
//            props.put("mail.smtp.starttls.enable", "true");
//            props.put("mail.smtp.host", "smtp.gmail.com");
//            props.put("mail.smtp.port", 587);
//            props.put("mail.smtp.ssl.protocols", "TLSv1.2");
//
//            final String email = "luctcph35904@fpt.edu.vn";
//            final String pass = "kcwf lbha hrpq qeef";
//            Session s = Session.getInstance(props,
//                    new javax.mail.Authenticator() {
//                        protected PasswordAuthentication getPasswordAuthentication() {
//                            return new PasswordAuthentication(email, pass);
//                        }
//
//                    });
//            String sender = "luctcph35904@fpt.edu.vn";
//           // String receiver = txtEmail.getText();
//            String subject = "Thư mời";
//            String text = "Chào bạn\n"
//                    + "\n"
//                    + "Mình là Minh, thuộc bộ phận tuyển dụng của hệ thống bán giày thể thao Sport Shop\n"
//                    + "Bạn đã ứng tuyển thành công vào vị trí: Nhân viên\n"
//                    + "\n"
//                    + "Tài khoản của bạn :" + ""
//                    + "\n"
//                    + "Mật khẩu :" + passwd
//                    + "\n"
//                    + "Để có thể nắm rõ về chi tiết công việc của bạn, bạn vui lòng đến Phòng P401, FPT Polytechnic cơ sở Kiểu Mai nhé\n"
//                    + "Trân trọng!";
//            Message msg = new MimeMessage(s);
//            msg.setFrom(new InternetAddress(sender));
//            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
//            msg.setSubject(subject);
//            msg.setText(text);
//            Transport.send(msg);
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//
//    }



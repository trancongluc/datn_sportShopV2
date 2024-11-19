package com.example.sportshopv2.service;

import com.example.sportshopv2.dto.UserDTO;
import com.example.sportshopv2.dto.UserNhanVienDTO;
import com.example.sportshopv2.model.Account;
import com.example.sportshopv2.model.User;
import com.example.sportshopv2.repository.NhanVienRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class NhanVienService {

    @Autowired
    private NhanVienRepo nvRepository;

    @Autowired
    private JavaMailSender mailSender;

    public Page<User> getAllDesc(Pageable pageable) {
        Pageable sortedByNewest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
        return nvRepository.findAllEmp(sortedByNewest);
    }

    public Page<User> searchEmp(String keyword, Pageable pageable) {
        return nvRepository.searchEmp(keyword, pageable);
    }


    public void mailSend(String toEmail, String username, String password) {
        String subject = "Mời em về với đội của anh!";
        String body = "Chào bạn\n"
                + "\n"
                + "Mình là Minh, thuộc bộ phận tuyển dụng của hệ thống bán giày thể thao SportShopV2\n"
                + "Bạn đã ứng tuyển thành công vào vị trí: Nhân viên\n"
                + "\n"
                + "Tài khoản của bạn :\n"
                + "Username : " + username
                + "\n"
                + "Mật khẩu : " + password
                + "\n"
                + "Để có thể nắm rõ về chi tiết công việc của bạn, bạn vui lòng đến Phòng P401, FPT Polytechnic cơ sở Kiểu Mai nhé\n"
                + "Trân trọng!";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail); // Địa chỉ email người nhận
        message.setSubject(subject); // Tiêu đề email
        message.setText(body); // Nội dung email
        message.setFrom("hellcatsuper053@gmail.com"); // Địa chỉ email người gửi

        mailSender.send(message);

    }

    public void deleteEmpById(Integer id) {
        nvRepository.deleteById(id); // Delete the customer by ID
    }

    private final String UPLOAD_DIR = "C:\\Users\\panha\\OneDrive\\Documents\\GitHub\\datn_sportShopV2\\sportShopV2\\src\\main\\resources\\static\\uploads\\";

    public User addnv(User NhanVien, MultipartFile imageFile, String role, String username, String password) throws IOException {
        // Lưu tệp hình ảnh
        if (!imageFile.isEmpty()) {
            String fileName = imageFile.getOriginalFilename();
            imageFile.transferTo(new File(UPLOAD_DIR + fileName));
            NhanVien.setImageFileName(fileName);
        }
        Account account = new Account();
        account.setUser(NhanVien);
        account.setRole(role); // Đặt role là "Customer"
        account.setUsername(username); // Hoặc bạn có thể thiết lập một username khác
        account.setPassword(password); // Thiết lập mật khẩu mặc định (bạn có thể mã hóa mật khẩu ở đây)
        account.setStatus("Active");
        NhanVien.setAccount(account); // Gán tài khoản cho user


        // Lưu khách hàng cùng với địa chỉ
        return nvRepository.save(NhanVien);
    }

    public User getEmpById(Integer id) {
        return nvRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")); // Handle user not found appropriately
    }

    public void updateNhanVien(User NhanVien) {
        nvRepository.save(NhanVien); // Save the updated customer
    }

    public void updateEmpImage(User NhanVien, MultipartFile imageFile) throws IOException {
        // Save the new image
        String fileName = imageFile.getOriginalFilename();
        imageFile.transferTo(new File(UPLOAD_DIR + fileName));
        NhanVien.setImageFileName(fileName);
    }
    public UserNhanVienDTO getNVById(Integer id) {
        User user = nvRepository.getNhanVienById(id);
        UserNhanVienDTO nvDTO = User.toNVDTO(user,nvRepository);
        return nvDTO;
    }

}

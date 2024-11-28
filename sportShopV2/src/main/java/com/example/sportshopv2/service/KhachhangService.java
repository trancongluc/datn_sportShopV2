package com.example.sportshopv2.service;


    import com.example.sportshopv2.model.Address;
    import com.example.sportshopv2.repository.AddressRepository;
    import com.example.sportshopv2.repository.KhachHangRepository;

import com.example.sportshopv2.dto.UserDTO;
import com.example.sportshopv2.model.Account;
import com.example.sportshopv2.model.NguoiDung;
import com.example.sportshopv2.model.User;
import com.example.sportshopv2.repository.AddressRepository;
import com.example.sportshopv2.repository.KhachHangRepository;
import com.example.sportshopv2.repository.NguoiDungRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Optional;

@Service
public class KhachhangService {

    @Autowired
    private KhachHangRepository userRepository;
    @Autowired
    private NguoiDungRepo ndRepo;

    @Autowired
    private AddressRepository addressRepository;


    public Page<User> getAllCustomers(Pageable pageable) {
        Pageable sortedByNewest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
        return userRepository.findAllCustomers(sortedByNewest);
    }

    public Page<User> searchCustomers(String keyword, Pageable pageable) {
        return userRepository.searchCustomers(keyword, pageable);
    }

//        public void deleteCustomerById(Integer id) {
//            userRepository.deleteById(id); // Delete the customer by ID
//
//        }

    public void deleteCustomerById(Integer id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setDeleted(true); // Đánh dấu là đã xóa
            userRepository.save(user); // Lưu thực thể đã cập nhật
        }
    }


    private final String UPLOAD_DIR = "C:\\HOCTAP\\DATN\\sportShopV2\\sportShopV2\\src\\main\\resources\\static\\uploads";

    public User addKhachHang(User khachHang, MultipartFile imageFile) throws IOException {


        // Lưu tệp hình ảnh
        if (!imageFile.isEmpty()) {
            String fileName = imageFile.getOriginalFilename();
            imageFile.transferTo(new File(UPLOAD_DIR + fileName));
            khachHang.setImageFileName(fileName); // Thiết lập tên tệp hình ảnh trong KhachHang
        }
        Account account = new Account();
        account.setUser(khachHang);
        account.setRole("Employee"); // Đặt role là "Customer"
        account.setUsername(khachHang.getEmail()); // Hoặc bạn có thể thiết lập một username khác
        account.setPassword("123"); // Thiết lập mật khẩu mặc định (bạn có thể mã hóa mật khẩu ở đây)

//            account.setUsername(username); // Hoặc bạn có thể thiết lập một username khác
//            account.setPassword(password); // Thiết lập mật khẩu mặc định (bạn có thể mã hóa mật khẩu ở đây)
        account.setStatus("Active");

        khachHang.setAccount(account); // Gán tài khoản cho user



        // Lưu khách hàng cùng với địa chỉ
        return userRepository.save(khachHang);

    }
        public User findCustomerById(Integer customerId) {
            return userRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
        }

        public Address findAddressById(Integer addressId) {
            return addressRepository.findById(addressId).orElseThrow(() -> new RuntimeException("Address not found"));


    }


    public User getCustomerById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")); // Handle user not found appropriately
    }

    public UserDTO getKHById(Integer id) {
        User userKH = userRepository.getKhachHangById(id);
        UserDTO khDTO = User.toDTO(userKH, id, userRepository);
        return khDTO;
    }

    public User getKHByIdThemHoaDon(Integer id) {
        User userKH = userRepository.getKhachHangById(id);
        return userKH;
    }

    public void updateKhachHang(User khachHang) {
        userRepository.save(khachHang); // Save the updated customer
    }

    public void updateCustomerImage(User customer, MultipartFile imageFile) throws IOException {
        // Save the new image
        String fileName = imageFile.getOriginalFilename();
        imageFile.transferTo(new File(UPLOAD_DIR + fileName));
        customer.setImageFileName(fileName);
    }


    public User findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public void save(User customer) {
        userRepository.save(customer);
    }

    public NguoiDung saveKH(NguoiDung customer) {
        String customerCode = generateCustomerCode();
        // Gán mã khách hàng vào tài khoản
        customer.setCode(customerCode);
        return ndRepo.save(customer);
    }

    private String generateCustomerCode() {
        // Tạo UUID và rút gọn
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
        return "KH" + uuid; // Ví dụ: KH4F3A8D2B
    }

    public List<NguoiDung> getKHCbo() {
        return ndRepo.findAllKhachHang();
    }
    public Optional<User> findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber); // Cần phải có phương thức này trong repository
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email); // Cần phải có phương thức này trong repository
    }




}

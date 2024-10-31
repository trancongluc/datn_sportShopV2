    package com.example.sportshopv2.Service;


    import com.example.sportshopv2.Repository.AddressRepository;
    import com.example.sportshopv2.Repository.KhachHangRepository;

    import com.example.sportshopv2.entity.Account;
    import com.example.sportshopv2.entity.User;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.PageRequest;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.domain.Sort;
    import org.springframework.stereotype.Service;
    import org.springframework.web.multipart.MultipartFile;

    import java.io.File;
    import java.io.IOException;

    @Service
    public class KhachhangService {

        @Autowired
        private KhachHangRepository userRepository;

        @Autowired
        private AddressRepository addressRepository;

        public Page<User> getAllCustomers(Pageable pageable) {
            Pageable sortedByNewest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            return userRepository.findAllCustomers(sortedByNewest);
        }

        public Page<User> searchCustomers(String keyword, Pageable pageable) {
            return userRepository.searchCustomers(keyword, pageable);
        }

        public void deleteCustomerById(Integer id) {
            userRepository.deleteById(id); // Delete the customer by ID
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
            account.setRole("Customer"); // Đặt role là "Customer"
            account.setUsername(khachHang.getEmail()); // Hoặc bạn có thể thiết lập một username khác
            account.setPassword("defaultPassword"); // Thiết lập mật khẩu mặc định (bạn có thể mã hóa mật khẩu ở đây)

            khachHang.setAccount(account); // Gán tài khoản cho user


            // Lưu khách hàng cùng với địa chỉ
            return userRepository.save(khachHang);
        }

        public User getCustomerById(Integer id) {
            return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")); // Handle user not found appropriately
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



//
//
//
//        public Page<User> findAll(Pageable pageable) {
//            return khachHangRepository.findAll(pageable);
//        }
//
//        public Page<User> searchKhachHang(String keyword, Pageable pageable) {
//            return khachHangRepository.searchByKeyword(keyword, pageable);
//        }
//
//        public void deleteCustomerById(Integer id) {
//            khachHangRepository.deleteById(id); // Delete the customer by ID
//        }
//
//
//        private final String UPLOAD_DIR = "C:\\Users\\Dungvt22\\Pictures\\w.ảnh";
//        public User addKhachHang(User khachHang, MultipartFile imageFile) throws IOException {
//            // Lưu tệp hình ảnh
//            if (!imageFile.isEmpty()) {
//                String fileName = imageFile.getOriginalFilename();
//                imageFile.transferTo(new File(UPLOAD_DIR + fileName));
//                khachHang.setImageFileName(fileName); // Thiết lập tên tệp hình ảnh trong KhachHang
//            }
//
//            // Lưu khách hàng cùng với địa chỉ
//            return khachHangRepository.save(khachHang);
//        }
//

    }

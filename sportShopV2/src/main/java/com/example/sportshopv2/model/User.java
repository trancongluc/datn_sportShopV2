package com.example.sportshopv2.model;

import com.example.sportshopv2.dto.UserDTO;
import com.example.sportshopv2.dto.UserNhanVienDTO;
import com.example.sportshopv2.repository.KhachHangRepository;
import com.example.sportshopv2.repository.NhanVienRepo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;



@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "\"User\"")


public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "code")
    private String code;

    @Column(name = "full_name")

    private String fullName;

    @Column(name = "phone_number")
    @NotNull(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Số điện thoại phải có từ 10 đến 15 chữ số")
    private String phoneNumber;

    @Column(name = "email")
    @Email
    private String email;
    @Column(name = "gender")
    private String gender;

    @Column(name = "date")
    private String date;
    @Column(name = "points")
    private int points;

    @Column(name = "image_file_name")  // Thêm trường tên file ảnh
    private String imageFileName;

    @OneToMany(mappedBy = "khachHang", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Address> addresses = new ArrayList<>();

    //
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude // Loại bỏ vòng lặp trong hashCode() và equals()

    private Account account;


    @Column(name = "create_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP) // Đảm bảo lưu trữ đúng kiểu thời gian
    private Date createdAt;

    @Column(name = "create_by", length = 50)
    private String createdBy;

    @Column(name = "update_at")
    @Temporal(TemporalType.TIMESTAMP) // Đảm bảo lưu trữ đúng kiểu thời gian
    private Date updatedAt;

    @Column(name = "update_by", length = 50)
    private String updatedBy;

    @Column(name = "deleted", columnDefinition = "BIT DEFAULT 0")
    private Boolean deleted = false;

    // Phương thức tiện ích để thêm địa chỉ
    private static final AtomicInteger COUNTER = new AtomicInteger(1);



    public void addAddress(Address address) {
        addresses.add(address);
        address.setKhachHang(this);
    }

    @PrePersist // Được gọi trước khi thực thể được lưu vào cơ sở dữ liệu
    private void onCreate() {
        if (this.getCode() == null || this.getCode().isEmpty()) {
            this.setCode(generateUniqueCode());
        }
        createdAt = new Date();
    }

    public String generateUniqueCode() {
        return "KH" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    @PreUpdate // Được gọi trước khi thực thể được cập nhật
    private void onUpdate() {
        updatedAt = new Date();
    }

    public static User of(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setCode(dto.getCode());
        user.setFullName(dto.getFullName());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setEmail(dto.getEmail());
        user.setGender(dto.getGender());
        user.setDate(dto.getDate());
        user.setPoints(dto.getPoints());
        user.setImageFileName(dto.getImageFileName());
        // Nếu cần, bạn có thể thêm các trường khác ở đây
        return user;
    }
    public static UserDTO toDTO(User user, Integer idUser, KhachHangRepository khRepo) {
        UserDTO dto = new UserDTO();
        user = khRepo.findById(idUser).orElse(null);
        dto.setId(user.getId());
        dto.setCode(user.getCode());
        dto.setFullName(user.getFullName());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setEmail(user.getEmail());
        dto.setGender(user.getGender());
        dto.setDate(user.getDate());
        dto.setPoints(user.getPoints());
        dto.setImageFileName(user.getImageFileName());
        return dto;
    }
    public static UserNhanVienDTO toNVDTO(User user,NhanVienRepo nvRepo) {
        user=nvRepo.findById(9).orElse(null);
        UserNhanVienDTO dto = new UserNhanVienDTO();
        dto.setId(user.getId());
        dto.setCode(user.getCode());
        dto.setFullName(user.getFullName());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setEmail(user.getEmail());
        dto.setGender(user.getGender());
        dto.setDate(user.getDate());
        dto.setPoints(user.getPoints());
        dto.setImageFileName(user.getImageFileName());
        return dto;
    }
    public static User of(UserNhanVienDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setCode(dto.getCode());
        user.setFullName(dto.getFullName());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setEmail(dto.getEmail());
        user.setGender(dto.getGender());
        user.setDate(dto.getDate());
        user.setPoints(dto.getPoints());
        user.setImageFileName(dto.getImageFileName());
        // Nếu cần, bạn có thể thêm các trường khác ở đây
        return user;
    }

}

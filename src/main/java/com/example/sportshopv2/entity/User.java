package com.example.sportshopv2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    private String phoneNumber;
    @Column(name = "email")
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
    private boolean deleted = false;

    // Phương thức tiện ích để thêm địa chỉ


    public void addAddress(Address address) {
        addresses.add(address);
        address.setKhachHang(this);
    }
    @PrePersist // Được gọi trước khi thực thể được lưu vào cơ sở dữ liệu
    private void onCreate() {
        createdAt = new Date();
    }

    @PreUpdate // Được gọi trước khi thực thể được cập nhật
    private void onUpdate() {
        updatedAt = new Date();
    }





}

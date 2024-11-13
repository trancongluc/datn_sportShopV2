package com.example.sportshopv2.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "\"User\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
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

    @OneToMany(mappedBy = "NhanVien", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude // Loại bỏ vòng lặp trong hashCode() và equals()
    private Account account;

    // Phương thức tiện ích để thêm địa chỉ
    public void addAddress(Address address) {
        addresses.add(address);
        address.setNhanVien(this);
    }
}

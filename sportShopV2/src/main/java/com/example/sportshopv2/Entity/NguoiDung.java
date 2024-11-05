package com.example.sportshopv2.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "\"User\"")
public class NguoiDung {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String code;
    private String full_name;
    private LocalDateTime date;
    @Column(name ="phone_number")
    private String phone_number;
    private String email;
    private String gender;
    private String image_file_name;
//    @Column(name = "points")
//    private String points;
}

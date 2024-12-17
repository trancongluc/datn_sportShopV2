package com.example.sportshopv2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Address")

public class Diachi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "province_name")
    private String tinh;
    @Column(name = "district_name")
    private String quan;
    @Column(name = "ward_name")
    private String phuong;
    @Column(name = "line")
    private String line;
    @Column(name = "province_id")
    private Integer province_id;
    @Column(name = "district_id")
    private Integer district_id;
    @Column(name = "ward_id")
    private Integer ward_id;

    @ManyToOne
    @JoinColumn(name = "id_User")
    @JsonIgnore
    private NguoiDung khachHang;


    @Column(name = "create_at", columnDefinition = "DATETIME DEFAULT GETDATE()")
    private Date createdAt = new Date();

    @Column(name = "create_by", length = 50)
    private String createdBy;

    @Column(name = "update_at")
    private Date updatedAt;

    @Column(name = "update_by", length = 50)
    private String updatedBy;


}

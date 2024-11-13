package com.example.sportshopv2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Address")
public class Address {
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
    @JoinColumn(name = "id_user")
    private User NhanVien;
//    @Column(name = "created_at")
//    private Date created_at;
//    @Column(name = "created_by")
//    private String created_by;
//    @Column(name = "updated_at")
//    private Date updated_at;
//    @Column(name = "updated_by")
//    private String updated_by;
//    @Column(name = "deleted")
//    private Integer deleted;


}

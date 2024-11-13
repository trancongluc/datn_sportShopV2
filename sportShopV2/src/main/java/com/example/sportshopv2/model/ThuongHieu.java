package com.example.sportshopv2.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Brand")
public class ThuongHieu extends BaseEntity{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String tenThuongHieu;
    @Column(name = "status")
    private String trangThai;

    public ThuongHieu(Integer id) {
        this.id = id;
    }
}

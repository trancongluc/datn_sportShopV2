package com.example.sportshopv2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Promotion")
public class DotGiamGia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "discount")
    private BigDecimal discount;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startDate;

    @Column(name = "end_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endDate;

    @Column(name = "status")
    private String status;

    @Column(name = "update_at")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime updateAt;

    @Column(name = "create_at")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createAt;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "deleted")
    private Boolean deleted;

    @OneToMany(mappedBy = "dotGiamGia", fetch = FetchType.LAZY)
    private List<DotGiamGiaChiTiet> dotGiamGiaChiTietList;


    public boolean isActive() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("Thời gian hiện tại: " + now);
        return startDate != null && endDate != null && startDate.isBefore(now) && endDate.isAfter(now);
    }


    @PrePersist
    @PreUpdate
    public void updateStatus() {
        this.status = getStatus();
    }

    public String getStatus() {
        LocalDateTime now = LocalDateTime.now();
        String statusText;

        if (startDate != null && endDate != null) {
            if (startDate.isAfter(now)) {
                statusText = "Chưa diễn ra";
            } else if (endDate.isBefore(now)) {
                statusText = "Hết hạn";  // Khi hết hạn, phải cập nhật trạng thái là "Hết hạn"

            } else {
                statusText = "Đang diễn ra";
            }
        } else {
            statusText = "Không xác định";
        }

        return statusText;
    }


}
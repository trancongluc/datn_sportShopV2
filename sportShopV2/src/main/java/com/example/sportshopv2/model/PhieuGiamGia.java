package com.example.sportshopv2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Voucher")
public class PhieuGiamGia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "voucher_code")
    private String voucherCode;

    @Column(name = "name")
    private String name;

    @Column(name = "discount_value")
    private BigDecimal discountValue;

    @Column(name = "minimum_value")
    private BigDecimal minimumValue;

    @Column(name = "form_voucher")
    private String formVoucher;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "start_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startDate;

    @Column(name = "end_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endDate;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "deleted")
    private Boolean deleted;

    @PrePersist
    @PreUpdate
    public void updateStatus() {
        // Gọi phương thức getStatus() để cập nhật trạng thái trước khi lưu hoặc cập nhật
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
            } else if (quantity == 0) {
                statusText = "Số lượng đã hết";
            } else {
                statusText = "Đang diễn ra";
            }
        } else {
            statusText = "Không xác định";
        }

        return statusText;
    }

}
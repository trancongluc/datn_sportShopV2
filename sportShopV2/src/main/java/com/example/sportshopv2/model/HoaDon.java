package com.example.sportshopv2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Bill")
public class HoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "id_account")
    private TaiKhoan account;
    @ManyToOne
    @JoinColumn(name = "id_staff")
    private TaiKhoan id_staff;

    @NotBlank(message = "{message.username}")
    @Column(name = "user_name")
    private String user_name;

    @NotBlank(message = "{message.phonenumber}")
    @Column(name = "phone_number")
    private String phone_number;

    @NotNull(message = "{message.totalmoney}")
    @Column(name = "total_money")
    private Float total_money;
    @NotNull(message = "{message.totalmoney}")
    @Column(name = "money_reduced")
    private Float money_reduced;

    @NotBlank(message = "{message.status}")
    @Column(name = "status")
    private String status;
    @Column(name="address")
    private String address;

    @Column(name = "ship_date")
    private String ship_date;

    @Column(name = "receive_date")
    private String receive_date;

    @Column(name = "confirmation_date")
    private String confirmation_date;

    @Column(name = "desire_date")
    private String desire_date;
    @Column(name = "value_point")

    private Integer value_point;
    @Column(name = "point_use")
    private Integer point_use;

    @Column(name = "money_ship")
    private Float money_ship;

    @Column(name = "pay_method")
    private String pay_method;

    @Column(name = "pay_status")
    private String pay_status;

    @Column(name = "bill_code")
    private String bill_code;

    @Column(name = "transaction_date")
    private String transaction_date;
    @Column(name = "email")
    private String email;
    @Column(name = "type")
    private String type;
    @Column(name = "note")
    private String note;

    @Column(name = "create_at")
    private LocalDateTime create_at;

    @Column(name = "create_by")
    private String create_by;

    @Column(name = "update_at")
    private LocalDateTime update_at;

    @Column(name = "update_by")
    private String update_by;
    @Column(name = "deleted")
    private Boolean deleted;

    @OneToMany(mappedBy = "hoaDon", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HoaDonChiTiet> billDetails;

    public String getStatusDisplay() {
        switch (status) {
            case "ORDERED": return "Chờ xác nhận";
            case "CONFIRMED": return "Đã xác nhận";
            case "SHIPPING": return "Chờ giao hàng";
            case "SHIPPED": return "Đang vận chuyển";
            case "COMPLETE": return "Hoàn thành";
            default: return "Chưa rõ";
        }
    }

    public int getStatusProgress() {
        switch (status) {
            case "ORDERED": return 25;
            case "CONFIRMED": return 50;
            case "SHIPPING": return 75;
            case "SHIPPED": return 90;
            case "COMPLETE": return 100;
            default: return 0;
        }
    }


}

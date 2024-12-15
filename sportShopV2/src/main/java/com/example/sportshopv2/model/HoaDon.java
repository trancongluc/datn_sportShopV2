package com.example.sportshopv2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

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
    private TaiKhoan id_account;
    @ManyToOne
    @JoinColumn(name = "id_staff")
    private TaiKhoan id_staff;

    @NotBlank(message = "{message.username}")
    @Column(name = "user_name")
    private String user_name;

    @Column(name = "phone_number")
    private String phone_number;


    @Column(name = "total_money")
    private Float total_money;

    @Column(name = "money_reduced")
    private Float money_reduced;


    @Column(name = "status")
    private String status;
    @Column(name = "address")
    private String address;

    @Column(name = "ship_date")
    private LocalDateTime ship_date;

    @Column(name = "receive_date")
    private LocalDateTime receive_date;

    @Column(name = "confirmation_date")
    private LocalDateTime confirmation_date;

    @Column(name = "desire_date")
    private LocalDateTime desire_date;
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
    private String billCode;

    @Column(name = "transaction_date")
    private LocalDateTime transaction_date;
    @Column(name = "email")
    private String email;
    @Column(name = "type")
    private String type;
    @Column(name = "note")
    private String note;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "create_by")
    private String create_by;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @Column(name = "update_by")
    private String update_by;
    @Column(name = "deleted")
    private Boolean deleted;


    public String getFormattedTotalMoney() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return formatter.format(this.total_money);
    }

    @OneToMany(mappedBy = "hoaDon", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<HoaDonChiTiet> billDetails;


    public String getStatusDisplay() {
        switch (status) {
            case "Chờ xác nhận":
                return "Chờ xác nhận";
            case "Đã xác nhận":
                return "Đã xác nhận";
            case "Chờ vận chuyển":
                return "Chờ vận chuyển";
            case "Đang vận chuyển":
                return "Đang vận chuyển";
            case "Hoàn thành":
                return "Hoàn thành";
            default:
                return "Chưa rõ";
        }
    }

    public int getStatusProgress() {
        switch (status) {
            case "Chờ xác nhận":
                return 25;
            case "Đã xác nhận":
                return 50;
            case "Chờ vận chuyển":
                return 75;
            case "Đang vận chuyển":
                return 90;
            case "Hoàn thành":
                return 100;
            default:
                return 0;
        }
    }


}

package com.example.sportshopv2.dto;

import com.example.sportshopv2.model.BaseEntity;
import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HistoryDTO {
    private Integer id;

    private String user_name;

    private Date create_at;

    private String status;

    private String total_money;

    private String address;

    private String tenSanPham;


}

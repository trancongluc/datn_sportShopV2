package com.example.sportshopv2.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SanPhamGiamGiaDTO {
    private Long productId;
    private String productName;
    private Long promotionId;
    private String promotionName;
    private BigDecimal discountValue;
    private LocalDate startDate;
    private LocalDate endDate;
}

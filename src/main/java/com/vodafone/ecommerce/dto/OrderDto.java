package com.vodafone.ecommerce.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {
    String createdAt;
    private Long id;
    private Double totalPrice;
    private Integer totalQuantity;
}

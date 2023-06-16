package com.vodafone.ecommerce.dto;

import lombok.*;

import java.util.List;

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
    private List<OrderItemDto> orderItemList;
}

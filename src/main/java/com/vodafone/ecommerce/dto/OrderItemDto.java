package com.vodafone.ecommerce.dto;

import com.vodafone.ecommerce.model.Product;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDto {
    private Long id;
    private Product product;
    private long quantity;
    private double totalPrice;
}

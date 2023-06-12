package com.vodafone.ecommerce.dto;

import com.vodafone.ecommerce.model.Order;
import com.vodafone.ecommerce.model.Product;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDto {
    private Long id;
    private Order order;
    private Product product;
    private long quantity;
}

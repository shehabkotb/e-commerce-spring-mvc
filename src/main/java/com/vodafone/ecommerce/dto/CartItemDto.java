package com.vodafone.ecommerce.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItemDto {
    private Long id;
    private ProductDto productDto;
    private Long quantity;
    private Long CartId;
}

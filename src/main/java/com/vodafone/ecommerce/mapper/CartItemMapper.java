package com.vodafone.ecommerce.mapper;

import com.vodafone.ecommerce.dto.CartItemDto;
import com.vodafone.ecommerce.dto.ProductDto;
import com.vodafone.ecommerce.model.CartItem;
import com.vodafone.ecommerce.model.Product;

import static com.vodafone.ecommerce.mapper.ProductMapper.mapToProductDto;

public class CartItemMapper {
    public static CartItemDto mapToCartItemDto(CartItem cartItem) {
        return CartItemDto.builder()
                .id(cartItem.getId())
                .productDto(mapToProductDto(cartItem.getProduct()))
                .CartId(cartItem.getShoppingCart().getId())
                .quantity(cartItem.getQuantity())
                .build();
    }
}

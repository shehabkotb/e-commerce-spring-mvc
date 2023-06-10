package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.dto.ProductDto;

public interface CartService {
    boolean isUserCartExist(Long userId);
    boolean addProductToCart(Long productId, Long quantity, Long userId);
}

package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.dto.ProductDto;

public interface CartService {
    boolean isUserCartExist(String username);
    void addProductToCart(ProductDto productDto, long quantity, String username);
}

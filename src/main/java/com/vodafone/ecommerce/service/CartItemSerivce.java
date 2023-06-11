package com.vodafone.ecommerce.service;

public interface CartItemSerivce {
    boolean isProductExist(Long productId);
    double calculateTotalPrice(Long cartId);
}

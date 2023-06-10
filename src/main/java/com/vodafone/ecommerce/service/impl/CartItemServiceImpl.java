package com.vodafone.ecommerce.service.impl;

import com.vodafone.ecommerce.repository.CartItemRepository;
import com.vodafone.ecommerce.service.CartItemSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartItemServiceImpl implements CartItemSerivce {
    @Autowired
    CartItemRepository cartItemRepository;
    @Override
    public boolean isProductExist(Long productId) {
        return cartItemRepository.findByProductId(productId) != null;
    }
}

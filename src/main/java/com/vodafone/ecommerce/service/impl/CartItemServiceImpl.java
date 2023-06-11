package com.vodafone.ecommerce.service.impl;

import com.vodafone.ecommerce.model.CartItem;
import com.vodafone.ecommerce.repository.CartItemRepository;
import com.vodafone.ecommerce.service.CartItemSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemServiceImpl implements CartItemSerivce {
    @Autowired
    CartItemRepository cartItemRepository;
    @Override
    public boolean isProductExist(Long productId) {
        return cartItemRepository.findByProductId(productId) != null;
    }

    @Override
    public double calculateTotalPrice(Long cartID) {
        List<CartItem> cartItems = cartItemRepository.findAllByShoppingCartId(cartID);
        double totalPrice = 0;
        for (CartItem cartItem: cartItems) {
            totalPrice += cartItem.getQuantity() * cartItem.getProduct().getPrice();
        }
        return totalPrice;
    }
}

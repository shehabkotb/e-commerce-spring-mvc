package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.model.CartItem;

import java.util.List;

public interface CartItemService {
    boolean isProductExist(Long productId);
    double calculateTotalPrice(Long cartId);
    CartItem findByProductId(Long productId);
    void saveCartItem(CartItem cartItem);
    List<CartItem> findAllByCartId(Long cartId);
    CartItem findByCartIdAndProductId(Long cartId, Long productId);
    void deleteCartItem(Long cartItemId);
    void deleteAll(List<CartItem> cartItemList);
}

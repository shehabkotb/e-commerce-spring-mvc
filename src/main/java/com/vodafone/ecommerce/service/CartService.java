package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.dto.CartItemDto;
import com.vodafone.ecommerce.dto.ProductDto;
import com.vodafone.ecommerce.model.CartItem;

import java.util.List;

public interface CartService {
    boolean isUserCartExist(Long userId);
    boolean addProductToCart(Long productId, Long quantity, Long userId);
    List<CartItemDto> getUserCart(Long userId);
    double getCartTotalPrice(Long userId);
    void deleteProductFromCart(Long userId, Long productId);
    boolean updateCartProduct(Long userId, Long productId, Long newQuantity);

}

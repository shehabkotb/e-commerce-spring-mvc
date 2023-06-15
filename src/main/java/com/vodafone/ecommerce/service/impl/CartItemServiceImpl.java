package com.vodafone.ecommerce.service.impl;

import com.vodafone.ecommerce.exception.NotFoundException;
import com.vodafone.ecommerce.model.CartItem;
import com.vodafone.ecommerce.repository.CartItemRepository;
import com.vodafone.ecommerce.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemServiceImpl implements CartItemService {
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

    @Override
    public CartItem findByProductId(Long productId) {
        return cartItemRepository.findByProductId(productId);
    }

    @Override
    public void saveCartItem(CartItem cartItem) {
        cartItemRepository.save(cartItem);
    }

    @Override
    public List<CartItem> findAllByCartId(Long cartId) {
        return cartItemRepository.findAllByShoppingCartId(cartId);
    }

    @Override
    public CartItem findByCartIdAndProductId(Long cartId, Long productId) {
        return cartItemRepository.findByShoppingCartIdAndProductId(cartId, productId);
    }

    @Override
    public void deleteCartItem(Long cartItemId) {
        cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new NotFoundException("This Cart doesn't exist."));
        cartItemRepository.deleteById(cartItemId);
    }

    @Override
    public void deleteAll(List<CartItem> cartItemList) {

    }
}

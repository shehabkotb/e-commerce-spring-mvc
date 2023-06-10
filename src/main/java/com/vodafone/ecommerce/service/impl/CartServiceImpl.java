package com.vodafone.ecommerce.service.impl;

import com.vodafone.ecommerce.dto.ProductDto;
import com.vodafone.ecommerce.model.CartItem;
import com.vodafone.ecommerce.model.ShoppingCart;
import com.vodafone.ecommerce.model.UserEntity;
import com.vodafone.ecommerce.repository.CartItemRepository;
import com.vodafone.ecommerce.repository.CartRepository;
import com.vodafone.ecommerce.repository.UserRepository;
import com.vodafone.ecommerce.security.SecurityUtil;
import com.vodafone.ecommerce.service.CartItemSerivce;
import com.vodafone.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.vodafone.ecommerce.mapper.ProductMapper.mapToProduct;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    CartItemSerivce cartItemSerivce;
    @Override
    public boolean isUserCartExist(String username) {
        UserEntity user = userRepository.findByUsername(username);
        return cartRepository.findByUserId(user.getId()) != null;
    }
    @Override
    public void addProductToCart(ProductDto productDto, long quantity, String username) {
        UserEntity user = userRepository.findByUsername(username);
        if(isUserCartExist(username)) {
            ShoppingCart cart = cartRepository.findByUserId(user.getId());
            double newTotalPrice = cart.getTotalPrice() + (productDto.getPrice() * quantity);
            cart.setTotalPrice(newTotalPrice);
            cartRepository.save(cart);

            CartItem cartItem;
            if(cartItemSerivce.isProductExist(productDto.getId())) {
                cartItem = cartItemRepository.findByProductId(productDto.getId());
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
            }
            else {
                cartItem = CartItem.builder()
                        .product(mapToProduct(productDto))
                        .shoppingCart(cart)
                        .quantity(quantity)
                        .build();
            }
            cartItemRepository.save(cartItem);
        }
        else {
            ShoppingCart cart = ShoppingCart.builder()
                    .totalPrice(productDto.getPrice())
                    .user(user)
                    .build();

            CartItem cartItem = CartItem.builder()
                    .product(mapToProduct(productDto))
                    .shoppingCart(cart)
                    .quantity(quantity)
                    .build();

            List<CartItem> cartItems = List.of(cartItem);
            cart.setCartItems(cartItems);
            cartRepository.save(cart);
            cartItemRepository.save(cartItem);
        }
    }
}

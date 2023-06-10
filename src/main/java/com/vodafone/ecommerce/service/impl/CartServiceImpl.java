package com.vodafone.ecommerce.service.impl;

import com.vodafone.ecommerce.dto.ProductDto;
import com.vodafone.ecommerce.model.CartItem;
import com.vodafone.ecommerce.model.Product;
import com.vodafone.ecommerce.model.ShoppingCart;
import com.vodafone.ecommerce.model.UserEntity;
import com.vodafone.ecommerce.repository.CartItemRepository;
import com.vodafone.ecommerce.repository.CartRepository;
import com.vodafone.ecommerce.repository.ProductRepository;
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
    @Autowired
    ProductRepository productRepository;
    @Override
    public boolean isUserCartExist(Long userId) {
        return cartRepository.findByUserId(userId) != null;
    }
    @Override
    public boolean addProductToCart(Long productId, Long quantity, Long userId) {
        Product product = productRepository.findById(productId).orElseThrow();
        if(product.getStockQuantity() >= quantity) {
            UserEntity user = userRepository.findById(userId).orElseThrow();
            if(isUserCartExist(userId)) {
                ShoppingCart cart = cartRepository.findByUserId(user.getId());
                double newTotalPrice = cart.getTotalPrice() + (product.getPrice() * quantity);
                cart.setTotalPrice(newTotalPrice);
                cartRepository.save(cart);

                CartItem cartItem;
                if(cartItemSerivce.isProductExist(product.getId())) {
                    cartItem = cartItemRepository.findByProductId(product.getId());
                    cartItem.setQuantity(cartItem.getQuantity() + quantity);
                }
                else {
                    cartItem = CartItem.builder()
                            .product(product)
                            .shoppingCart(cart)
                            .quantity(quantity)
                            .build();
                }
                cartItemRepository.save(cartItem);
            }
            else {
                ShoppingCart cart = ShoppingCart.builder()
                        .totalPrice(product.getPrice())
                        .user(user)
                        .build();

                CartItem cartItem = CartItem.builder()
                        .product(product)
                        .shoppingCart(cart)
                        .quantity(quantity)
                        .build();

                List<CartItem> cartItems = List.of(cartItem);
                cart.setCartItems(cartItems);
                cartRepository.save(cart);
                cartItemRepository.save(cartItem);
            }
            long newStockQuantity = product.getStockQuantity() - quantity;
            product.setStockQuantity(newStockQuantity);
            productRepository.save(product);
            return true;
        }
        return false;
    }
}

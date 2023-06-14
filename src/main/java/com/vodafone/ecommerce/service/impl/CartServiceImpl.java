package com.vodafone.ecommerce.service.impl;

import com.vodafone.ecommerce.dto.CartItemDto;
import com.vodafone.ecommerce.exception.NotFoundException;
import com.vodafone.ecommerce.mapper.CartItemMapper;
import com.vodafone.ecommerce.model.CartItem;
import com.vodafone.ecommerce.model.Product;
import com.vodafone.ecommerce.model.ShoppingCart;
import com.vodafone.ecommerce.model.UserEntity;
import com.vodafone.ecommerce.repository.CartItemRepository;
import com.vodafone.ecommerce.repository.CartRepository;
import com.vodafone.ecommerce.repository.ProductRepository;
import com.vodafone.ecommerce.repository.UserRepository;
import com.vodafone.ecommerce.service.CartItemSerivce;
import com.vodafone.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("This Product doesn't exist."));
        if (product.getStockQuantity() >= quantity) {
            UserEntity user = userRepository.findById(userId)
                    .orElseThrow(() -> new NotFoundException("This User doesn't exist."));
            if (isUserCartExist(userId)) {
                ShoppingCart cart = cartRepository.findByUserId(user.getId());
                double newTotalPrice = cart.getTotalPrice() + (product.getPrice() * quantity);
                cart.setTotalPrice(newTotalPrice);
                cartRepository.save(cart);

                CartItem cartItem;
                if (cartItemSerivce.isProductExist(product.getId())) {
                    cartItem = cartItemRepository.findByProductId(product.getId());
                    cartItem.setQuantity(cartItem.getQuantity() + quantity);
                } else {
                    cartItem = CartItem.builder()
                            .product(product)
                            .shoppingCart(cart)
                            .quantity(quantity)
                            .build();
                }
                cartItemRepository.save(cartItem);
            } else {
                ShoppingCart cart = ShoppingCart.builder()
                        .totalPrice(product.getPrice() * quantity)
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

    @Override
    public List<CartItemDto> getUserCart(Long userId) {
        if(userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException("This User doesn't exist.");
        }
        if (!isUserCartExist(userId)) {
            return new ArrayList<>();
        }
        Long cartId = cartRepository.findByUserId(userId).getId();
        List<CartItem> cartItemsList = cartItemRepository.findAllByShoppingCartId(cartId);
        return cartItemsList.stream().map(CartItemMapper::mapToCartItemDto).collect(Collectors.toList());
    }

    @Override
    public double getCartTotalPrice(Long userId) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException("This User doesn't exist.");
        }
        if (!isUserCartExist(userId)) {
            return 0;
        }
        return cartRepository.findByUserId(userId).getTotalPrice();
    }

    @Override
    public void deleteProductFromCart(Long userId, Long productId) {
        ShoppingCart cart = cartRepository.findByUserId(userId);
        CartItem cartItem = cartItemRepository.findByShoppingCartIdAndProductId(cart.getId(), productId);
        Product product = productRepository.findById(productId).orElseThrow();
        cart.setTotalPrice(cart.getTotalPrice() - (product.getPrice() * cartItem.getQuantity()));
        product.setStockQuantity(product.getStockQuantity() + cartItem.getQuantity());
        productRepository.save(product);
        cartItemRepository.deleteById(cartItem.getId());
        cartRepository.save(cart);
    }

    @Override
    public boolean updateCartProduct(Long userId, Long productId, Long newQuantity) {
        ShoppingCart cart = cartRepository.findByUserId(userId);
        CartItem cartItem = cartItemRepository.findByShoppingCartIdAndProductId(cart.getId(), productId);
        Product product = productRepository.findById(productId).orElseThrow();
        Long availableQuantity = product.getStockQuantity() + cartItem.getQuantity();
        if (availableQuantity >= newQuantity) {
            product.setStockQuantity(availableQuantity - newQuantity);
            cartItem.setQuantity(newQuantity);
            cart.setTotalPrice(cartItemSerivce.calculateTotalPrice(cart.getId()));
            productRepository.save(product);
            cartItemRepository.save(cartItem);
            cartRepository.save(cart);
            return true;
        }
        return false;
    }

    @Override
    public int getCartTotalItemCount(Long userId) {
        ShoppingCart cart = cartRepository.findByUserId(userId);

        if (cart == null) {
            return 0;
        }
        return cart.getCartItems().stream().reduce(0, (total, cartItem) -> total + cartItem.getQuantity().intValue(), Integer::sum);
    }
}

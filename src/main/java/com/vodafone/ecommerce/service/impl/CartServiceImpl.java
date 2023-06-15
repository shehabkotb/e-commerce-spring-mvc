package com.vodafone.ecommerce.service.impl;

import com.vodafone.ecommerce.dto.CartItemDto;
import com.vodafone.ecommerce.exception.NotFoundException;
import com.vodafone.ecommerce.mapper.CartItemMapper;
import com.vodafone.ecommerce.model.CartItem;
import com.vodafone.ecommerce.model.Product;
import com.vodafone.ecommerce.model.ShoppingCart;
import com.vodafone.ecommerce.model.UserEntity;
import com.vodafone.ecommerce.repository.CartRepository;
import com.vodafone.ecommerce.service.CartItemService;
import com.vodafone.ecommerce.service.CartService;
import com.vodafone.ecommerce.service.ProductService;
import com.vodafone.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.vodafone.ecommerce.mapper.ProductMapper.mapToProduct;
import static com.vodafone.ecommerce.mapper.ProductMapper.mapToProductDto;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cartRepository;
    @Autowired
    UserService userService;
    @Autowired
    CartItemService cartItemService;
    @Autowired
    ProductService productService;

    @Override
    public boolean isUserCartExist(Long userId) {
        return cartRepository.findByUserId(userId) != null;
    }

    @Override
    public boolean addProductToCart(Long productId, Long quantity, Long userId) {
        Product product = mapToProduct(productService.findProductById(productId));
        if (product.getStockQuantity() >= quantity) {
            UserEntity user = userService.getUserById(userId);
            if (isUserCartExist(userId)) {
                ShoppingCart cart = cartRepository.findByUserId(user.getId());
                double newTotalPrice = cart.getTotalPrice() + (product.getPrice() * quantity);
                cart.setTotalPrice(newTotalPrice);
                cartRepository.save(cart);

                CartItem cartItem;
                if (cartItemService.isProductExist(product.getId())) {
                    cartItem = cartItemService.findByProductId(product.getId());
                    cartItem.setQuantity(cartItem.getQuantity() + quantity);
                } else {
                    cartItem = CartItem.builder()
                            .product(product)
                            .shoppingCart(cart)
                            .quantity(quantity)
                            .build();
                }
                cartItemService.saveCartItem(cartItem);
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
                cartItemService.saveCartItem(cartItem);
            }
            long newStockQuantity = product.getStockQuantity() - quantity;
            product.setStockQuantity(newStockQuantity);
            productService.saveProduct(mapToProductDto(product));
            return true;
        }
        return false;
    }

    @Override
    public List<CartItemDto> getUserCart(Long userId) {
        if(userService.getUserById(userId) == null) {
            throw new NotFoundException("This User doesn't exist.");
        }
        if (!isUserCartExist(userId)) {
            return new ArrayList<>();
        }
        Long cartId = cartRepository.findByUserId(userId).getId();
        List<CartItem> cartItemsList = cartItemService.findAllByCartId(cartId);
        return cartItemsList.stream().map(CartItemMapper::mapToCartItemDto).collect(Collectors.toList());
    }

    @Override
    public double getCartTotalPrice(Long userId) {
        if (userService.getUserById(userId) == null) {
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
        CartItem cartItem = cartItemService.findByCartIdAndProductId(cart.getId(), productId);
        Product product = mapToProduct(productService.findProductById(productId));
        cart.setTotalPrice(cart.getTotalPrice() - (product.getPrice() * cartItem.getQuantity()));
        product.setStockQuantity(product.getStockQuantity() + cartItem.getQuantity());
        productService.saveProduct(mapToProductDto(product));
        cartItemService.deleteCartItem(cartItem.getId());
        cartRepository.save(cart);
    }

    @Override
    public boolean updateCartProduct(Long userId, Long productId, Long newQuantity) {
        ShoppingCart cart = cartRepository.findByUserId(userId);
        CartItem cartItem = cartItemService.findByCartIdAndProductId(cart.getId(), productId);
        Product product = mapToProduct(productService.findProductById(productId));
        Long availableQuantity = product.getStockQuantity() + cartItem.getQuantity();
        if (availableQuantity >= newQuantity) {
            product.setStockQuantity(availableQuantity - newQuantity);
            cartItem.setQuantity(newQuantity);
            cart.setTotalPrice(cartItemService.calculateTotalPrice(cart.getId()));
            productService.saveProduct(mapToProductDto(product));
            cartItemService.saveCartItem(cartItem);
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

    @Override
    public ShoppingCart findByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    @Override
    public void saveCart(ShoppingCart cart) {
        cartRepository.save(cart);
    }
}

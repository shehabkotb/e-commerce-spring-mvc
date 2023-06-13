package com.vodafone.ecommerce.controller;

import com.vodafone.ecommerce.dto.CartItemDto;
import com.vodafone.ecommerce.security.CustomUserDetails;
import com.vodafone.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CartController {
    @Autowired
    CartService cartService;

    @PreAuthorize("#authUser.id == #userId")
    @GetMapping("/user/{userId}/cart")
    public String getCart(@PathVariable("userId") Long userId, Model model, @AuthenticationPrincipal CustomUserDetails authUser) {
        model.addAttribute("cartItemsDto", cartService.getUserCart(userId));
        model.addAttribute("totalPrice", cartService.getCartTotalPrice(userId));
        return "customer-cart";
    }

    @PreAuthorize("#authUser.id == #userId")
    @PostMapping("/user/{userId}/cart/{productId}/add")
    public String addProductToCart(@PathVariable("userId") Long userId,
                                   @PathVariable("productId") Long productId,
                                   @ModelAttribute("quantity") Long quantity,
                                   @AuthenticationPrincipal CustomUserDetails authUser) {
        if(cartService.addProductToCart(productId, quantity, userId)) {
            return "redirect:/products/" + productId + "?success=true";
        }
        return "redirect:/products/" + productId + "?failed=true";
    }

    @PreAuthorize("#authUser.id == #userId")
    @PostMapping("/user/{userId}/cart/{productId}/delete")
    public String deleteProductFromCart(@PathVariable("userId") Long userId,
                                        @PathVariable("productId") Long productId,
                                        @AuthenticationPrincipal CustomUserDetails authUser) {
        cartService.deleteProductFromCart(userId, productId);
        return "redirect:/user/{userId}/cart";
    }

    @PreAuthorize("#authUser.id == #userId")
    @PostMapping("/user/{userId}/cart/{productId}/update")
    public String updateCartProduct(@PathVariable("userId") Long userId,
                                    @PathVariable("productId") Long productId,
                                    @ModelAttribute("quantity") Long newQuantity,
                                    @AuthenticationPrincipal CustomUserDetails authUser) {
        if (cartService.updateCartProduct(userId, productId, newQuantity)) {
            return "redirect:/user/{userId}/cart?success=true";
        }
        return "redirect:/user/{userId}/cart?failed=true";
    }
}

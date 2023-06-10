package com.vodafone.ecommerce.controller;

import com.vodafone.ecommerce.security.SecurityUtil;
import com.vodafone.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/cart")
    public String getCart(Model model) {
        return "customer-cart";
    }

    public String getCurrentUserName() {
        return SecurityUtil.getSessionUser();
    }

    @PostMapping("/user/{userId}/cart/{productId}/add")
    public String addProductToCart(@PathVariable("userId") Long userId,
                                   @PathVariable("productId") Long productId,
                                   @ModelAttribute("quantity") Long quantity) {
        if(cartService.addProductToCart(productId, quantity, userId)) {
            return "redirect:/products/" + productId + "?success=true";
        }
        return "redirect:/products/" + productId + "?failed=true";
    }

}

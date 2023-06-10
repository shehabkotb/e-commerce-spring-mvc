package com.vodafone.ecommerce.controller;

import com.vodafone.ecommerce.security.SecurityUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CartController {
    @GetMapping("/cart")
    public String getCart(Model model) {
        return "customer-cart";
    }

    public String getCurrentUserName() {
        return SecurityUtil.getSessionUser();
    }

}

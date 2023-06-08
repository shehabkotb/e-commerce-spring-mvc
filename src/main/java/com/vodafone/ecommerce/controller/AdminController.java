package com.vodafone.ecommerce.controller;


import com.vodafone.ecommerce.dto.ProductDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {

    @GetMapping(value = {"/admin", "admin/dashboard"})
    public String adminDashboard(Model model) {
        return "admin-dashboard";
    }

    @GetMapping("admin/users")
    public String adminUsersManagement(Model model) {
        return "admin-users";
    }

    @GetMapping("admin/products")
    public String adminProductManagement(Model model) {
        return "admin-products";
    }

}

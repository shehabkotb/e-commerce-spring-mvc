package com.vodafone.ecommerce.controller;


import com.vodafone.ecommerce.dto.ProductDto;
import com.vodafone.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    ProductService productService;

    @GetMapping(value = {"/admin", "/admin/dashboard"})
    public String adminDashboard(Model model) {
        return "admin-dashboard";
    }

    @GetMapping("/admin/users")
    public String adminUsersManagement(Model model) {
        return "admin-users";
    }

    @GetMapping("/admin/products")
    public String adminProductManagement(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "admin-products";
    }

    @GetMapping("/admin/products/add")
    public String adminAddProduct(Model model) {
        return "admin-addProduct";
    }

    @PostMapping("/admin/products/add")
    public String adminSaveProduct(@ModelAttribute("product") ProductDto productDto, BindingResult bindingResult, Model model) {
        productService.saveProduct(productDto);
        return "redirect:/admin/products";
    }
}

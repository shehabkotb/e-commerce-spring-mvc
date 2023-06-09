package com.vodafone.ecommerce.controller;


import com.vodafone.ecommerce.dto.ProductDto;
import com.vodafone.ecommerce.dto.UserEntityDto;
import com.vodafone.ecommerce.model.Product;
import com.vodafone.ecommerce.model.UserEntity;
import com.vodafone.ecommerce.service.ProductService;
import com.vodafone.ecommerce.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
public class AdminController {

    private final ProductService productService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping(value = {"/admin", "/admin/dashboard"})
    public String adminDashboard(Model model) {
        return "admin-dashboard";
    }

    @GetMapping("/admin/users")
    public String adminUsersManagement(Model model) {
        model.addAttribute("UserEntity", userService.getAllAdmin());
        return "admin-users";
    }

    @GetMapping("/admin/products")
    public String adminProductManagement(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "admin-products";
    }

    @GetMapping("/admin/products/add")
    public String adminAddProduct(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "admin-addProduct";
    }

    @PostMapping("/admin/products/add")
    public String adminSaveProduct(@Valid @ModelAttribute("product") ProductDto productDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("product", productDto);
            return "admin-addProduct";
        }

        productService.saveProduct(productDto);
        return "redirect:/admin/products";
    }


    @GetMapping("/admin/users/add")
    public String adminAddAdmin(Model model) {
        UserEntity user = new UserEntity();
        model.addAttribute("UserEntity", user);
        return "admin-addAdmin";
    }
    @PostMapping("/admin/users/add")
    public String adminSaverAdmin(@Valid @ModelAttribute("UserEntity") UserEntityDto userEntityDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("UserEntity", userEntityDto);
            return "admin-addAdmin";
        }
        String encryptedPassword=passwordEncoder.encode(userEntityDto.getPassword());
        userEntityDto.setPassword(encryptedPassword);
        userService.saveAdmin(userEntityDto);
        return "redirect:/admin/users";

    }
}

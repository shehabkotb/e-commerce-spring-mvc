package com.vodafone.ecommerce.controller;


import com.vodafone.ecommerce.dto.ProductDto;
import com.vodafone.ecommerce.dto.AdminDto;
import com.vodafone.ecommerce.model.Product;
import com.vodafone.ecommerce.model.UserEntity;
import com.vodafone.ecommerce.service.ProductService;
import com.vodafone.ecommerce.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

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
    public String adminSaverAdmin(@Valid @ModelAttribute("UserEntity") AdminDto adminDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("UserEntity", adminDto);
            return "admin-addAdmin";
        }
        String encryptedPassword=passwordEncoder.encode(adminDto.getPassword());
        adminDto.setPassword(encryptedPassword);
        userService.saveAdmin(adminDto);
        return "redirect:/admin/users";

    }

    @GetMapping("/admin/{adminId}/edit")
    public String editAdminForm(@PathVariable("adminId") Long adminId, Model model) {
        AdminDto user = userService.findAdminById(adminId);
        model.addAttribute("UserEntity", user);
        return "admin-editAdmin";
    }

    @PostMapping("/admin/{adminId}/edit")
    public String updateAdmin(@PathVariable("adminId") Long adminId,
                             @Valid @ModelAttribute("UserEntity") AdminDto admin,
                             BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("UserEntity", admin);
            return "admin-editAdmin";
        }
        admin.setId(adminId);
        String encryptedPassword=passwordEncoder.encode(admin.getPassword());
        admin.setPassword(encryptedPassword);
        userService.updateClub(admin);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/{adminId}/delete")
    public String deleteClub(@PathVariable("adminId")Long adminId) {
        userService.delete(adminId);
        return "redirect:/admin/users";
    }



}

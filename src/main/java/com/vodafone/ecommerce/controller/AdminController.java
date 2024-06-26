package com.vodafone.ecommerce.controller;


import com.vodafone.ecommerce.dto.ProductDto;
import com.vodafone.ecommerce.dto.UserDto;
import com.vodafone.ecommerce.model.Product;
import com.vodafone.ecommerce.model.UserEntity;
import com.vodafone.ecommerce.service.AdminService;
import com.vodafone.ecommerce.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class AdminController {

    @Autowired
    private ProductService productService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AdminService adminService;


    @GetMapping(value = {"/admin", "/admin/dashboard"})
    public String adminDashboard(Model model) {
        return "admin-dashboard";
    }

    @GetMapping("/admin/users")
    public String adminUsersManagement(Model model) {
        model.addAttribute("UserEntity", adminService.getAllAdmin());
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

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/admin/users/add")
    public String adminAddAdmin(Model model) {
        UserEntity user = new UserEntity();
        model.addAttribute("UserEntity", user);
        return "admin-addAdmin";
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping("/admin/users/add")
    public String adminSaverAdmin(@Valid @ModelAttribute("UserEntity") UserDto userDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("UserEntity", userDto);
            return "admin-addAdmin";
        }
        String encryptedPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encryptedPassword);
        adminService.saveAdmin(userDto);
        return "redirect:/admin/users";

    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/admin/{adminId}/edit")
    public String editAdminForm(@PathVariable("adminId") Long adminId, Model model) {
        UserDto user = adminService.findAdminById(adminId);
        model.addAttribute("UserEntity", user);
        return "admin-editAdmin";
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping("/admin/{adminId}/edit")
    public String updateAdmin(@PathVariable("adminId") Long adminId,
                              @Valid @ModelAttribute("UserEntity") UserDto admin,
                              BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("UserEntity", admin);
            return "admin-editAdmin";
        }
        adminService.updateAdmin(admin, adminId);
        return "redirect:/admin/users";
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/admin/{adminId}/delete")
    public String deleteAdmin(@PathVariable("adminId") Long adminId) {
        adminService.deleteAdmin(adminId);
        return "redirect:/admin/users";
    }


    @GetMapping("/admin/products/{productId}/delete")
    public String adminDeleteProduct(@PathVariable("productId") Long productId) {
        productService.deleteProduct(productId);
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/products/{productId}/edit")
    public String adminEditProductForm(@PathVariable("productId") Long productId, Model model) {
        ProductDto productDto = productService.findProductById(productId);
        model.addAttribute("product", productDto);
        return "admin-editProduct";
    }

    @PostMapping("/admin/products/edit")
    public String adminEditProduct(@Valid @ModelAttribute("product") ProductDto productDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("product", productDto);
            return "admin-editProduct";
        }
        productService.updateProduct(productDto);
        return "redirect:/admin/products";
    }
}

package com.vodafone.ecommerce.controller;

import com.vodafone.ecommerce.dto.ProductDto;
import com.vodafone.ecommerce.service.ProductService;
import com.vodafone.ecommerce.util.ProductInitializr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ProductController {
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = {"/", "/products"})
    public String listProducts(Model model) {
        List<ProductDto> products = productService.findAllProducts();

        model.addAttribute("products", products);
        return "products-list";
    }

    @GetMapping("/products/{productId}")
    public String productDetail(@PathVariable("productId") long productId, Model model) {
        ProductDto product = productService.findProductById(productId);

        model.addAttribute("product", product);
        return "products-detail";
    }
}

package com.vodafone.ecommerce.controller;

import com.vodafone.ecommerce.dto.ProductDto;
import com.vodafone.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;


    @GetMapping(value = {"/", "/products"})
    public String listProducts(@RequestParam(required = false, defaultValue = "") String searchBy, @RequestParam(required = false, defaultValue = "") String query, Model model) {
        List<ProductDto> products;

        if (searchBy.equalsIgnoreCase("category")) {
            products = productService.findAllProductsByCategory(query);
        } else if (searchBy.equalsIgnoreCase("name")) {
            products = productService.findAllProductsByName(query);
        } else {
            products = productService.findAllProducts();
        }

        products = products.stream().filter((p) -> !p.getDeleted()).collect(Collectors.toList());

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

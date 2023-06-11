package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.dto.ProductDto;
import com.vodafone.ecommerce.model.Product;

import java.util.List;


public interface ProductService {
    List<ProductDto> findAllProducts();

    List<ProductDto> findAllProductsByCategory(String query);

    List<ProductDto> findAllProductsByName(String query);

    ProductDto findProductById(Long productId);

    Product saveProduct(ProductDto productDto);

    List<Product> getAllProducts();

    void deleteProduct(Long productId);

    void updateProduct(ProductDto productDto);

}

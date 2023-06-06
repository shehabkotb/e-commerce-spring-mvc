package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.dto.ProductDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ProductService {
    List<ProductDto> findAllProducts();
    ProductDto findProductById(long productId);
}

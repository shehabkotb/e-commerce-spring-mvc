package com.vodafone.ecommerce.service.impl;

import com.vodafone.ecommerce.dto.ProductDto;
import com.vodafone.ecommerce.service.ProductService;
import com.vodafone.ecommerce.util.ProductInitializr;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    //hardcoded for now
    List<ProductDto> productList = ProductInitializr.getInitProducts();

    @Override
    public List<ProductDto> findAllProducts() {
        return productList;
    }

    @Override
    public ProductDto findProductById(long productId) {
        return productList.stream().filter(p -> p.getId() == productId).findFirst().get();
    }
}

package com.vodafone.ecommerce.service.impl;

import com.vodafone.ecommerce.dto.ProductDto;
import com.vodafone.ecommerce.model.Product;
import com.vodafone.ecommerce.repository.ProductRepository;
import com.vodafone.ecommerce.service.ProductService;
import com.vodafone.ecommerce.util.ProductInitializr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.vodafone.ecommerce.mapper.ProductMapper.mapToProduct;

@Service
public class ProductServiceImpl implements ProductService {

    //hardcoded for now
    List<ProductDto> productList = ProductInitializr.getInitProducts();

    @Autowired
    ProductRepository productRepository;
    @Override
    public List<ProductDto> findAllProducts() {
        return productList;
    }

    @Override
    public ProductDto findProductById(long productId) {
        return productList.stream().filter(p -> p.getId() == productId).findFirst().get();
    }

    @Override
    public Product saveProduct(ProductDto productDto) {
        Product product = mapToProduct(productDto);
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}

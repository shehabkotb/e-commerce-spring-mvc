package com.vodafone.ecommerce.service.impl;

import com.vodafone.ecommerce.dto.ProductDto;
import com.vodafone.ecommerce.exception.NotFoundException;
import com.vodafone.ecommerce.mapper.ProductMapper;
import com.vodafone.ecommerce.model.Product;
import com.vodafone.ecommerce.repository.ProductRepository;
import com.vodafone.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.vodafone.ecommerce.mapper.ProductMapper.mapToProduct;
import static com.vodafone.ecommerce.mapper.ProductMapper.mapToProductDto;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<ProductDto> findAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductMapper::mapToProductDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> findAllProductsByCategory(String query) {
        return productRepository.findAll()
                .stream()
                .filter((i) -> i.getCategory().name().toLowerCase().contains(query.toLowerCase()))
                .map(ProductMapper::mapToProductDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> findAllProductsByName(String query) {
        return productRepository.findByNameContaining(query).stream().map(ProductMapper::mapToProductDto).collect(Collectors.toList());
    }

    @Override
    public ProductDto findProductById(Long productId) {
        return mapToProductDto(productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("This Product doesn't exist.")));
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

    @Override
    public void deleteProduct(Long productId) {
        productRepository.findById(productId).orElseThrow(() ->
                new NotFoundException("This Product doesn't exist."));
        productRepository.deleteById(productId);
    }

    @Override
    public void updateProduct(ProductDto productDto) {
        Product product = mapToProduct(productDto);
        productRepository.save(product);
    }

}

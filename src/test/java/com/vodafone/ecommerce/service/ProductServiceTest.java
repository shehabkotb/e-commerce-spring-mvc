package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.dto.ProductDto;
import com.vodafone.ecommerce.enums.Category;
import com.vodafone.ecommerce.exception.NotFoundException;
import com.vodafone.ecommerce.model.Product;
import com.vodafone.ecommerce.repository.ProductRepository;
import com.vodafone.ecommerce.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ProductService.class)
class ProductServiceTest {
    @Mock
    ProductRepository productRepository;
    @InjectMocks
    ProductServiceImpl productService;

    @Test
    void findAllProductsByCategoryTest_findAllProductsByCategory_thenReturnProductWithThisCategory() {
        //Arrange
        Product product1 = Product.builder()
                .category(Category.LAPTOPS)
                .build();
        Product product2 = Product.builder()
                .category(Category.MOBILE)
                .build();
        Product product3 = Product.builder()
                .category(Category.LAPTOPS)
                .build();

        List<Product> products = Arrays.asList(product1, product2, product3);
        //Act
        when(productRepository.findAll()).thenReturn(products);

        ProductDto productDto1 = ProductDto.builder()
                .category(Category.LAPTOPS)
                .build();
        ProductDto productDto2 = ProductDto.builder()
                .category(Category.LAPTOPS)
                .build();

        List<ProductDto> expectedProducts = Arrays.asList(productDto1, productDto2);

        List<ProductDto> actualProducts = productService.findAllProductsByCategory(String.valueOf(Category.LAPTOPS));
        //Assert
        assertNotNull(actualProducts);
        assertEquals(expectedProducts.size(), actualProducts.size());


    }

    @Test
    void findAllProductsByName_findAllProductsByName_thenReturnProductWithThisName() {
        //Arrange
        Product product1 = Product.builder()
                .category(Category.LAPTOPS)
                .name("p1")
                .build();
        Product product2 = Product.builder()
                .category(Category.MOBILE)
                .name("p2")
                .build();
        Product product3 = Product.builder()
                .category(Category.LAPTOPS)
                .name("p1")
                .build();

        List<Product> products = Arrays.asList(product1, product2, product3);
        //Act
        when(productRepository.findByNameContaining("p1")).thenReturn(Arrays.asList(product1, product2));

        ProductDto productDto1 = ProductDto.builder()
                .category(Category.LAPTOPS)
                .name("p1")
                .build();
        ProductDto productDto2 = ProductDto.builder()
                .category(Category.LAPTOPS)
                .name("p1")
                .build();

        List<ProductDto> expectedProducts = Arrays.asList(productDto1, productDto2);


        List<ProductDto> actualProducts = productService.findAllProductsByName("p1");
        //Assert
        assertNotNull(actualProducts);
        assertEquals(expectedProducts.size(), actualProducts.size());


    }

    @Test
    void findAllProductsTest_findAllProducts_thenReturnAllProduct() {
        //Arrange
        Product product1 = Product.builder()
                .category(Category.LAPTOPS)
                .deleted(false)
                .build();
        Product product2 = Product.builder()
                .category(Category.MOBILE)
                .deleted(false)
                .build();
        Product product3 = Product.builder()
                .category(Category.LAPTOPS)
                .deleted(false)
                .build();

        List<Product> products = Arrays.asList(product1, product2, product3);
        //Act
        when(productRepository.findAll()).thenReturn(products);

        List<Product> actualProducts = productService.getAllProducts();
        //Assert
        assertNotNull(actualProducts);
        assertEquals(3, actualProducts.size());


    }

    @Test
    void findProductByIdTest_findProductById_returnProduct() {
        //Arrange
        Product product = Product.builder()
                .id(1L)
                .category(Category.MOBILE)
                .description("desc")
                .price(12.0)
                .name("lab")
                .stockQuantity(10L)
                .build();
        //Act

        when(productRepository.findById(1L)).thenReturn(Optional.ofNullable(product));
        ProductDto productDto = productService.findProductById(1L);
        //Assert
        assertNotNull(productDto);
        assertEquals(product.getStockQuantity(), productDto.getStockQuantity());
        assertEquals(product.getName(), productDto.getName());
        assertEquals(product.getDescription(), productDto.getDescription());
        assertEquals(product.getPrice(), productDto.getPrice());

    }

    @Test
    void findAllProductTest_findAllProduct_returnAllProduct() {
        //Arrange
        List<Product> products = new ArrayList<>();
        Product product1 = Product.builder()
                .id(1L)
                .category(Category.MOBILE)
                .description("desc")
                .price(12.0)
                .name("lab")
                .stockQuantity(10L)
                .build();
        Product product2 = Product.builder()
                .id(1L)
                .category(Category.MOBILE)
                .description("desc")
                .price(12.0)
                .name("lab")
                .stockQuantity(10L)
                .build();
        products.add(product1);
        products.add(product2);
        //Act

        when(productRepository.findAll()).thenReturn(products);
        List<ProductDto> allProducts = productService.findAllProducts();
        //Assert
        assertNotNull(products);
        assertEquals(2, allProducts.size());

    }

    @Test
    void saveProductTest_saveProduct_returnProduct() {
        //Arrange
        Product product = Product.builder()
                .id(1L)
                .category(Category.MOBILE)
                .description("desc")
                .price(12.0)
                .name("lab")
                .stockQuantity(10L)
                .build();
        ProductDto productDto = ProductDto.builder()
                .id(1L)
                .category(Category.MOBILE)
                .description("desc")
                .price(12.0)
                .name("lab")
                .stockQuantity(10L)
                .build();
        //Act

        when(productRepository.save(any(Product.class))).thenReturn(product);
        Product product1 = productService.saveProduct(productDto);
        //Assert
        assertNotNull(product1);
        assertEquals(product.getDescription(), product1.getDescription());
        assertEquals(product.getCategory(), product1.getCategory());
        assertEquals(product.getName(), product1.getName());

    }


    @Test
    void deleteProductTest_deleteProductNotExist_throwNotFound() {
        //Arrange
        Product product = Product.builder()
                .id(1L)
                .category(Category.MOBILE)
                .description("desc")
                .price(12.0)
                .name("lab")
                .stockQuantity(10L)
                .build();
        //Act
        when(productRepository.findById(product.getId())).thenReturn(Optional.empty());

        //Assert
        assertThrows(NotFoundException.class, () -> productService.deleteProduct(product.getId()));
    }

    @Test
    void updateProductTest_updateProduct_returnUpdatedProduct() {
        //Arrange
        Product product = Product.builder()
                .id(1L)
                .category(Category.MOBILE)
                .description("desc")
                .price(12.0)
                .name("lab")
                .stockQuantity(10L)
                .build();
        ProductDto productDto = ProductDto.builder()
                .id(1L)
                .category(Category.MOBILE)
                .description("desc")
                .price(12.0)
                .name("lab")
                .stockQuantity(10L)
                .build();
        //Act

        when(productRepository.save(any(Product.class))).thenReturn(product);
        productService.updateProduct(productDto);
        //Assert
        verify(productRepository).save(any(Product.class));

    }

}

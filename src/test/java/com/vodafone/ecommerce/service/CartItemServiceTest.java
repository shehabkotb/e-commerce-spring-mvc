package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.model.CartItem;
import com.vodafone.ecommerce.model.Product;
import com.vodafone.ecommerce.repository.CartItemRepository;
import com.vodafone.ecommerce.service.CartItemService;
import com.vodafone.ecommerce.service.impl.CartItemServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
@ExtendWith(SpringExtension.class)

@SpringBootTest(classes = CartItemService.class)
 class CartItemServiceTest {
    @Mock
    CartItemRepository cartItemRepository;
    @InjectMocks
    private  CartItemServiceImpl cartItemSerivce;


    @Test
    void isProductExistTest_isProductExist_returnProduct(){
        //Arrange
        CartItem cartItem=CartItem.builder()
                .quantity(10L)
                .id(1L)
                .build();
        //Act
        when(cartItemRepository.findByProductId(1L)).thenReturn(cartItem);
         cartItemSerivce.isProductExist(cartItem.getId());
         //Assert
        assertNotNull(cartItem);
        assertEquals(10L,cartItem.getQuantity());

    }
    @Test
    void calculateTotalPriceTest_calculateTotalPrice_returnTotalPrice(){
        Product product1 = Product.builder()
                .id(1L)
                .name("Product1")
                .price(20.0)
                .build();
        Product product2 = Product.builder()
                .id(2L)
                .name("Product2")
                .price(20.0)
                .build();

        CartItem cartItem1=CartItem.builder()
                .quantity(2L)
                .product(product1)
                .id(1L)
                .build();
        CartItem cartItem2=CartItem.builder()
                .quantity(2L)
                .product(product2)
                .id(1L)
                .build();

        List<CartItem> cartItems = Arrays.asList(cartItem1, cartItem2);

        when(cartItemRepository.findAllByShoppingCartId(cartItem1.getId())).thenReturn(cartItems);

        double expectedTotalPrice = 80.0; // 2 x 20.0 + 2 x 20.0

        double actualTotalPrice = cartItemSerivce.calculateTotalPrice(cartItem1.getId());

        assertThat(actualTotalPrice).isEqualTo(expectedTotalPrice);


    }
}

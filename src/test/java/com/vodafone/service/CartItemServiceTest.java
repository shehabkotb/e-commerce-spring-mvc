package com.vodafone.service;

import com.vodafone.ecommerce.model.CartItem;
import com.vodafone.ecommerce.repository.CartItemRepository;
import com.vodafone.ecommerce.service.CartItemService;
import com.vodafone.ecommerce.service.impl.CartItemServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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

}
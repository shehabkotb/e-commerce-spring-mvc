package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.dto.OrderDto;
import com.vodafone.ecommerce.dto.RegistrationDto;
import com.vodafone.ecommerce.enums.Role;
import com.vodafone.ecommerce.enums.Status;
import com.vodafone.ecommerce.model.*;
import com.vodafone.ecommerce.repository.CartItemRepository;
import com.vodafone.ecommerce.repository.CartRepository;
import com.vodafone.ecommerce.repository.OrderRepository;
import com.vodafone.ecommerce.repository.UserRepository;
import com.vodafone.ecommerce.service.impl.CartServiceImpl;
import com.vodafone.ecommerce.service.impl.OrderServiceImpl;
import com.vodafone.ecommerce.service.impl.UserServiceImpl;
import lombok.Data;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest(classes = OrderService.class)
public class OrderServiceTest {
    @InjectMocks
    OrderServiceImpl orderService;
//    @InjectMocks
//    CartServiceImpl cartService;
//    @InjectMocks
//    UserServiceImpl userService;
    @Mock
    OrderRepository orderRepository;
    @Mock
    private CartRepository cartRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartItemRepository cartItemRepository;


    @Test
    void getAllOrdersByUserIdTest_getAllOrdersByUserId_returnOrderWithThisId(){
        //Arrange
        Product product1=Product.builder()
                .id(1L)
                .name("p1")
                .price(10.0)
                .build();
        Product product2=Product.builder()
                .id(2L)
                .name("p2")
                .price(10.0)
                .build();

        OrderItem orderItem1=OrderItem
                .builder()
                .id(1L)
                .product(product1)
                .quantity(2L)
                .build();
        OrderItem orderItem2=OrderItem
                .builder()
                .id(2L)
                .product(product2)
                .quantity(2L)
                .build();

        List<OrderItem> orderItems1 = Arrays.asList(orderItem1);
        List<OrderItem> orderItems2 = Arrays.asList(orderItem2);

        Order order1=Order.builder()
                .id(1L)
                .totalPrice(20.00)
                .createdAt(LocalDateTime.now())
                .orderItems(orderItems1)
                .build();
        Order order2=Order.builder()
                .id(1L)
                .createdAt(LocalDateTime.now())
                .totalPrice(20.00)
                .orderItems(orderItems2)
                .build();
        List<Order>orders= Arrays.asList(order1,order2);

        //Act
        when(orderRepository.findByUserId(order1.getId())).thenReturn(orders);
        List<OrderDto> orderDto = orderService.getAllOrdersByUserId(order1.getId());

        //Assert
        assertNotNull(orderDto);
        assertEquals(2,orderDto.size());
    }

//    @Test
//     void testCreateOrderAndEmptyCart() {
//        //Arrange
//        long userId=1;
//        Product product1=Product.builder()
//                .id(1L)
//                .name("p1")
//                .price(10.0)
//                .build();
//        Product product2=Product.builder()
//                .id(2L)
//                .name("p2")
//                .price(10.0)
//                .build();
//
//        CartItem cartItem1 =CartItem.builder()
//                .id(1L)
//                .product(product1)
//                .quantity(2L)
//                .build();
//        CartItem cartItem2 =CartItem.builder()
//                .id(2L)
//                .product(product2)
//                .quantity(2L)
//                .build();
//        List<CartItem> cartItems = Arrays.asList(cartItem1, cartItem2);
//
//        ShoppingCart shoppingCart=ShoppingCart.builder()
//                .id(1L)
//                .cartItems(cartItems)
//                .totalPrice(40.0)
//                .build();
//
//        UserEntity user=UserEntity.builder()
//                .id(userId)
//                .email("hema@example.com")
//                .password("pass")
//                .role(Role.USER)
//                .status(Status.ACTIVE)
//                .loginFailureCount(0)
//                .build();
//
//        Order order1=Order.builder()
//                .id(1L)
//                .totalPrice(20.00)
//                .createdAt(LocalDateTime.now())
//                .build();
//        //Act
//        when(cartRepository.findByUserId(1L)).thenReturn(shoppingCart);
//        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
//        when(orderRepository.save(any(Order.class))).thenReturn(order1);
//
//        orderService.createOrderAndEmptyCart(1L);
//        //Assert
//        verify(cartService, times(1)).findByUserId(1L);
//        verify(userService, times(1)).getUserById(1L);
//        verify(orderRepository, times(1)).save(any(Order.class));
//        verify(cartService, times(1)).saveCart(any(ShoppingCart.class));
//
//        assertThat(shoppingCart.getTotalPrice()).isEqualTo(0.0);
//    }

}




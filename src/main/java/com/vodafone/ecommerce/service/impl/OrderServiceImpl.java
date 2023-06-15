package com.vodafone.ecommerce.service.impl;

import com.vodafone.ecommerce.dto.OrderDto;
import com.vodafone.ecommerce.mapper.OrderMapper;
import com.vodafone.ecommerce.model.Order;
import com.vodafone.ecommerce.model.OrderItem;
import com.vodafone.ecommerce.model.ShoppingCart;
import com.vodafone.ecommerce.model.UserEntity;
import com.vodafone.ecommerce.repository.CartItemRepository;
import com.vodafone.ecommerce.repository.CartRepository;
import com.vodafone.ecommerce.repository.OrderRepository;
import com.vodafone.ecommerce.repository.UserRepository;
import com.vodafone.ecommerce.service.CartService;
import com.vodafone.ecommerce.service.OrderService;
import com.vodafone.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;
    @Autowired
    private CartItemRepository cartItemRepository;



    @Override
    public List<OrderDto> getAllOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream().map((order) -> OrderMapper.mapToOrderDto(order, getTotalOrderQuantity(order))).collect(Collectors.toList());
    }

    private Integer getTotalOrderQuantity(Order order) {
        return order.getOrderItems().stream().reduce(0, (total, orderItem) -> total + orderItem.getQuantity().intValue(), Integer::sum);
    }

    @Override
    public OrderDto getOrderById(Long orderId) {
        return null;
    }

    @Override
    public void createOrderAndEmptyCart(Long userId) {
        ShoppingCart shoppingCart = cartService.findByUserId(userId);
        UserEntity user = userService.getUserById(userId);

        Order newOrder = new Order();

        List<OrderItem> orderItemList = shoppingCart.getCartItems().stream().map(
                (i) -> OrderItem.builder()
                        .product(i.getProduct())
                        .quantity(i.getQuantity())
                        .order(newOrder)
                        .build()
        ).collect(Collectors.toList());


        newOrder.setUser(user);
        newOrder.setTotalPrice(shoppingCart.getTotalPrice());
        newOrder.setOrderItems(orderItemList);

        shoppingCart.setTotalPrice(0D);

        orderRepository.save(newOrder);
        cartService.saveCart(shoppingCart);
        cartItemRepository.deleteAll(shoppingCart.getCartItems());
    }
}

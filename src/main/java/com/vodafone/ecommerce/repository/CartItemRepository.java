package com.vodafone.ecommerce.repository;

import com.vodafone.ecommerce.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByProductId(Long productId);
    List<CartItem> findAllByShoppingCartId(Long cartId);
    void deleteByProductId(Long productId);
}

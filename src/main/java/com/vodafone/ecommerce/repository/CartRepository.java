package com.vodafone.ecommerce.repository;

import com.vodafone.ecommerce.model.ShoppingCart;
import com.vodafone.ecommerce.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<ShoppingCart, Long> {
    ShoppingCart findByUserId(Long userId);
}

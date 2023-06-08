package com.vodafone.ecommerce.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cart")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double totalPrice;
    @OneToOne
    private UserEntity user;
    @OneToMany(mappedBy = "shoppingCart")
    List<CartItem> cartItems;
}

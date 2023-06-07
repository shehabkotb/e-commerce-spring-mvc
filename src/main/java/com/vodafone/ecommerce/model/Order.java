package com.vodafone.ecommerce.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @CreationTimestamp
    LocalDateTime createdAt;
    private double totalPrice;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "order")
    List<OrderItem> orderItems;
}

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
    private Long id;
    @CreationTimestamp
    LocalDateTime createdAt;
    private Double totalPrice;
    @ManyToOne
    private UserEntity user;
    @OneToMany(mappedBy = "order")
    List<OrderItem> orderItems;
}

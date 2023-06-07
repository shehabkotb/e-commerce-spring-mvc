package com.vodafone.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_item")
public class OrderItem {
    @Id
    private Integer orderId;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Product product;
    private long quantity;
}

package com.vodafone.ecommerce.model;

import com.vodafone.ecommerce.enums.Category;
import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String photoUrl;
    @Enumerated(EnumType.STRING)
    private Category category;
    private long stockQuantity;
}

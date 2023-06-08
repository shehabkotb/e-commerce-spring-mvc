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
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private double price;
    private String photoUrl;
    @Enumerated(EnumType.STRING)
    private Category category;
}

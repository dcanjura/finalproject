package com.canjura.finalproject.entity.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.transaction.Transactional;

@Entity
@NoArgsConstructor
@Transactional
@Table(name = "products")
@Getter @Setter
public class Product {

    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int stock;
    private Double price;
}

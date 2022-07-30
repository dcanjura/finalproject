package com.canjura.finalproject.entity.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.transaction.Transactional;

@Entity
@NoArgsConstructor
@Transactional
@Table(name = "order_products")
@Getter @Setter
public class OrderProduct {
    @Id
    @Column(name = "order_product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int quantity;
}

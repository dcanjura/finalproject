package com.canjura.finalproject.entity.checkout;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.transaction.Transactional;

@Entity
@NoArgsConstructor
@Transactional
@Table(name = "checkout_products")
@Getter @Setter
public class CheckoutProduct {

    @Id
    @Column(name = "checkout_product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int quantity;
}

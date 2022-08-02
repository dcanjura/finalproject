package com.canjura.finalproject.entity.checkout;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Transactional
@Table(name = "checkout")
@Getter @Setter
public class Checkout {

    @Id
    @Column(name = "checkout_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String userEmail;
    private String addressInfo;
    private String paymentInfo;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_checkout_id", referencedColumnName = "checkout_id")
    private List<CheckoutProduct> products = new ArrayList<>();

    public Checkout(String userEmail, String addressInfo, String paymentInfo, List<CheckoutProduct> products) {
        this.userEmail = userEmail;
        this.addressInfo = addressInfo;
        this.paymentInfo = paymentInfo;
        this.products = products;
    }
}

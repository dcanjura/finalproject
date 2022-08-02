package com.canjura.finalproject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;

@Entity
@Transactional
@NoArgsConstructor
@Table(name = "user_payment")
@Getter @Setter
public class UserPayment {

    @Id
    @Column(name = "payment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Please enter the payment info")
    private String payment;

    @NotBlank(message = "Please enter the payment type")
    private String type;

    public UserPayment(String payment, String type) {
        this.payment = payment;
        this.type = type;
    }
}

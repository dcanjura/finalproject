package com.canjura.finalproject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;

@Entity
@Transactional
@Table(name = "user_address")
@NoArgsConstructor
@Getter @Setter
public class UserAddress {

    @Id
    @Column(name = "address_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Please enter the address info")
    private String address;

    @NotBlank(message = "Please enter the address type")
    private String type;

    public UserAddress(String address, String type) {
        this.address = address;
        this.type = type;
    }
}

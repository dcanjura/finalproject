package com.canjura.finalproject.entity.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Transactional
@Table(name = "useraddress")
@NoArgsConstructor
@Getter @Setter
public class UserAddress {

    @Id
    @Column(name = "address_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Address should not be null")
    @NotBlank(message = "Please enter the address")
    private String address;

    @NotNull(message = "Address should not be null")
    @NotBlank(message = "Please enter the address type")
    private String type;
}

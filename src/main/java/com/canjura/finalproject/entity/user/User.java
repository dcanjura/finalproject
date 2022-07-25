package com.canjura.finalproject.entity.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.Constraint;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Transactional
@Getter @Setter
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Please enter the name")
    @NotBlank(message = "Name cannot be null")
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    private String name;

    @NotBlank(message = "Please enter the phone number")
    @NotNull(message = "Phone cannot be null")
    @Size(min = 8, max = 12, message = "Phone number must be between 8 and 12 characters")
    @Pattern(regexp = "\\+503\\d{8}", message = "Phone must have pattern: +503########")
    private String phone;

    @Email(message = "Email is not a valid formatted email address")
    @NotNull(message = "Email cannot be null")
    @NotBlank(message = "Please enter the email")
    @Size(min = 8, max = 100,message = "Email must be between 8 and 100")
    private String email;

    @OneToMany(cascade = CascadeType.ALL)
    @UniqueElements
    @Valid
    @NotNull(message = "The address information should not be null")
    @JoinColumn(name = "fk_user_id", referencedColumnName = "user_id")
    private List<UserAddress> address;
}

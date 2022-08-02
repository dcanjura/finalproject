package com.canjura.finalproject.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class UserPaymentTest {
    private static Validator validator;
    Set<ConstraintViolation<UserPayment>> violations;
    private static UserPayment payment;

    @BeforeAll
    public static void createConfig(){
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
        payment = new UserPayment("4123654769531456", "Credit Card");
        payment.setId(1);
    }

    @Test
    @DisplayName("UserAddress Null Values")
    void userAddressNullValuesTest(){
        //Arr
        payment = new UserPayment();

        //Act
        violations = validator.validate(payment);

        //Assert
        Assertions.assertThat(violations).hasSize(2);
    }

    @Test
    @DisplayName("UserAddress Null Address Test")
    void userAddressNullAddressTest(){
        //Arr
        payment = new UserPayment("", "Credit Card");

        //Act
        violations = validator.validate(payment);

        //Assert
        Assertions.assertThat(violations).hasSize(1);
        ConstraintViolation<UserPayment> violation = violations.iterator().next();
        Assertions.assertThat(violation.getMessage()).isEqualTo("Please enter the payment info");
        Assertions.assertThat(violation.getPropertyPath().toString()).hasToString("payment");
    }

    @Test
    @DisplayName("UserAddress Null Type Test")
    void userAddressNullTypeTest(){
        //Arr
        payment = new UserPayment("Montrial 10 pje 5, Mejicanos", "");

        //Act
        violations = validator.validate(payment);

        //Assert
        Assertions.assertThat(violations).hasSize(1);
        ConstraintViolation<UserPayment> violation = violations.iterator().next();
        Assertions.assertThat(violation.getMessage()).isEqualTo("Please enter the payment type");
        Assertions.assertThat(violation.getPropertyPath().toString()).hasToString("type");
    }
}

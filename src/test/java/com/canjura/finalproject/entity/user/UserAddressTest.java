package com.canjura.finalproject.entity.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class UserAddressTest {
    private static Validator validator;
    Set<ConstraintViolation<UserAddress>> violations;
    private static UserAddress address;

    @BeforeAll
    public static void createConfig(){
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
        address = new UserAddress("Montrial 10 pje 5, Mejicanos", "Home");
        address.setId(1);
    }

    @Test
    @DisplayName("UserAddress Null Values")
    void userAddressNullValuesTest(){
        //Arr
        address = new UserAddress();

        //Act
        violations = validator.validate(address);

        //Assert
        Assertions.assertThat(violations).hasSize(2);
    }

    @Test
    @DisplayName("UserAddress Null Address Test")
    void userAddressNullAddressTest(){
        //Arr
        address = new UserAddress("", "Home");

        //Act
        violations = validator.validate(address);

        //Assert
        Assertions.assertThat(violations).hasSize(1);
        ConstraintViolation<UserAddress> violation = violations.iterator().next();
        Assertions.assertThat(violation.getMessage()).isEqualTo("Please enter the address info");
        Assertions.assertThat(violation.getPropertyPath().toString()).hasToString("address");
    }

    @Test
    @DisplayName("UserAddress Null Type Test")
    void userAddressNullTypeTest(){
        //Arr
        address = new UserAddress("Montrial 10 pje 5, Mejicanos", "");

        //Act
        violations = validator.validate(address);

        //Assert
        Assertions.assertThat(violations).hasSize(1);
        ConstraintViolation<UserAddress> violation = violations.iterator().next();
        Assertions.assertThat(violation.getMessage()).isEqualTo("Please enter the address type");
        Assertions.assertThat(violation.getPropertyPath().toString()).hasToString("type");
    }
}

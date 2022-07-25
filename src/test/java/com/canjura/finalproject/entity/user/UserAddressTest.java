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
    public void userAddressNullValuesTest(){
        //Arr
        address = new UserAddress();

        //Act
        violations = validator.validate(address);

        //Assert
        Assertions.assertThat(violations.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("UserAddress Null Address Test")
    public void userAddressNullAddressTest(){
        //Arr
        address = new UserAddress("", "Home");

        //Act
        violations = validator.validate(address);

        //Assert
        Assertions.assertThat(violations.size()).isEqualTo(1);
        ConstraintViolation<UserAddress> violation = violations.iterator().next();
        Assertions.assertThat("Please enter the address info").isEqualTo(violation.getMessage());
        Assertions.assertThat("address").isEqualTo(violation.getPropertyPath().toString());
    }

    @Test
    @DisplayName("UserAddress Null Type Test")
    public void userAddressNullTypeTest(){
        //Arr
        address = new UserAddress("Montrial 10 pje 5, Mejicanos", "");

        //Act
        violations = validator.validate(address);

        //Assert
        Assertions.assertThat(violations.size()).isEqualTo(1);
        ConstraintViolation<UserAddress> violation = violations.iterator().next();
        Assertions.assertThat("Please enter the address type").isEqualTo(violation.getMessage());
        Assertions.assertThat("type").isEqualTo(violation.getPropertyPath().toString());
    }
}

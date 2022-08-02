package com.canjura.finalproject.dto.checkout;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

class CheckoutProductDTOTest {

    private static ValidatorFactory validatorFactory;

    private static Validator validator;

    private static final CheckoutProductDTO product = new CheckoutProductDTO();

    @BeforeAll
    public static void createValidator(){
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    public static void close(){
        validatorFactory.close();
    }

    @Test
    @DisplayName("No violations")
    void checkoutProductDTONoViolationsTest(){
        //Arr
        product.setName("TV");
        product.setQuantity(1);

        //Act
        Set<ConstraintViolation<CheckoutProductDTO>> violations = validator.validate(product);

        //Assert
        Assertions.assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Null name and 0 quantity")
    void checkoutProductDTONullNameAndNoQuantityTest(){
        //Arr
        product.setName(null);
        product.setQuantity(0);

        //Act
        Set<ConstraintViolation<CheckoutProductDTO>> violations = validator.validate(product);

        //Assert
        Assertions.assertThat(violations).hasSize(2);
    }
}

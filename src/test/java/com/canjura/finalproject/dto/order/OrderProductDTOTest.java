package com.canjura.finalproject.dto.order;

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

class OrderProductDTOTest {

    private static ValidatorFactory validatorFactory;

    private static Validator validator;

    private static final OrderProductDTO productDTO = new OrderProductDTO();

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
    void noViolationsTest(){
        //Arr
        productDTO.setName("TV");
        productDTO.setQuantity(1);

        //Act
        Set<ConstraintViolation<OrderProductDTO>> violations = validator.validate(productDTO);

        //Assert
        Assertions.assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Null name and 0 product quantity")
    void nullNameAndNoProductQuantityTest(){
        //Arr
        productDTO.setName(null);
        productDTO.setQuantity(0);

        //Act
        Set<ConstraintViolation<OrderProductDTO>> violations = validator.validate(productDTO);

        //Assert
        Assertions.assertThat(violations).hasSize(2);
    }
}

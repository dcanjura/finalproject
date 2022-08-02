package com.canjura.finalproject.dto.product;

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

class ProductDTOTest {

    private static ValidatorFactory validatorFactory;

    private static Validator validator;

    private static final ProductDTO product = new ProductDTO();

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
        product.setName("TV");
        product.setStock(20);
        product.setPrice(400.0);

        //Act
        Set<ConstraintViolation<ProductDTO>> violations = validator.validate(product);

        //Assert
        Assertions.assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Null name")
    void nullNameTest(){
        //Arr
        product.setName(null);
        product.setStock(20);
        product.setPrice(400.0);

        //Act
        Set<ConstraintViolation<ProductDTO>> violations = validator.validate(product);

        //Assert
        Assertions.assertThat(violations).hasSize(3);
    }

    @Test
    @DisplayName("No stock")
    void noStockTest(){
        //Arr
        product.setName("TV");
        product.setStock(0);
        product.setPrice(400.0);

        //Act
        Set<ConstraintViolation<ProductDTO>> violations = validator.validate(product);

        //Assert
        Assertions.assertThat(violations).hasSize(1);
    }

    @Test
    @DisplayName("Null price")
    void nullPriceTest(){
        //Arr
        product.setName("TV");
        product.setStock(20);
        product.setPrice(null);

        //Act
        Set<ConstraintViolation<ProductDTO>> violations = validator.validate(product);

        //Assert
        Assertions.assertThat(violations).hasSize(2);
    }
}

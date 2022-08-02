package com.canjura.finalproject.dto.checkout;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

class CheckoutDTOTest {

    private static ValidatorFactory validatorFactory;

    private static Validator validator;

    private static final CheckoutDTO checkoutDTO = new CheckoutDTO();

    private static final List<CheckoutProductDTO> checkoutProductDTOS = new ArrayList<>();

    @BeforeAll
    public static void createValidator(){
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @BeforeEach
    public void createCheckoutDTOConfig(){
        CheckoutProductDTO productDTO = new CheckoutProductDTO();
        productDTO.setName("TV");
        productDTO.setQuantity(1);
        checkoutProductDTOS.add(productDTO);
    }

    @AfterAll
    public static void close(){
        validatorFactory.close();
    }

    @Test
    @DisplayName("CheckoutDTO no violations")
    void checkoutDTONoViolationsTest(){
        //Arr
        checkoutDTO.setEmail("juan@gmail.com");
        checkoutDTO.setAddressInfo("Home");
        checkoutDTO.setPaymentInfo("Credit Card");
        checkoutDTO.setProducts(checkoutProductDTOS);

        //Act
        Set<ConstraintViolation<CheckoutDTO>> violations = validator.validate(checkoutDTO);

        //Assert
        Assertions.assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("CheckoutDTO email violation")
    void userEntityEmailViolation(){
        //Arr
        checkoutDTO.setEmail("juan");
        checkoutDTO.setAddressInfo("Home");
        checkoutDTO.setPaymentInfo("Credit Card");
        checkoutDTO.setProducts(checkoutProductDTOS);

        //Act
        Set<ConstraintViolation<CheckoutDTO>> violations = validator.validate(checkoutDTO);

        //Assert
        Assertions.assertThat(violations).hasSize(1);
        ConstraintViolation<CheckoutDTO> violation = violations.iterator().next();
        Assertions.assertThat(violation.getMessage()).isEqualTo("Email should be a valid one");
        Assertions.assertThat(violation.getPropertyPath().toString()).hasToString("email");
    }

    @Test
    @DisplayName("CheckoutDTO null values")
    void checkoutDTONullValuesTest(){
        //Arr
        checkoutDTO.setEmail(null);
        checkoutDTO.setAddressInfo(null);
        checkoutDTO.setPaymentInfo(null);
        checkoutDTO.setProducts(null);

        //Act
        Set<ConstraintViolation<CheckoutDTO>> violations = validator.validate(checkoutDTO);

        //Assert
        Assertions.assertThat(violations).hasSize(11);
    }

    @Test
    @DisplayName("CheckoutDTO null email value")
    void checkoutDTONullEmailValueTest(){
        //Arr
        checkoutDTO.setEmail(null);
        checkoutDTO.setAddressInfo("Home");
        checkoutDTO.setPaymentInfo("Credit Card");
        checkoutDTO.setProducts(checkoutProductDTOS);

        //Act
        Set<ConstraintViolation<CheckoutDTO>> violations = validator.validate(checkoutDTO);

        //Assert
        Assertions.assertThat(violations).hasSize(3);
    }

    @Test
    @DisplayName("CheckoutDTO null address info value")
    void checkoutDTONullAddressValueTest(){
        //Arr
        checkoutDTO.setEmail("Juan");
        checkoutDTO.setAddressInfo(null);
        checkoutDTO.setPaymentInfo("Credit Card");
        checkoutDTO.setProducts(checkoutProductDTOS);

        //Act
        Set<ConstraintViolation<CheckoutDTO>> violations = validator.validate(checkoutDTO);

        //Assert
        Assertions.assertThat(violations).hasSize(4);
    }

    @Test
    @DisplayName("CheckoutDTO null payment info value")
    void checkoutDTONullPaymentValueTest(){
        //Arr
        checkoutDTO.setEmail("Juan");
        checkoutDTO.setAddressInfo("Home");
        checkoutDTO.setPaymentInfo(null);
        checkoutDTO.setProducts(checkoutProductDTOS);

        //Act
        Set<ConstraintViolation<CheckoutDTO>> violations = validator.validate(checkoutDTO);

        //Assert
        Assertions.assertThat(violations).hasSize(4);
    }

    @Test
    @DisplayName("CheckoutDTO null products value")
    void checkoutDTONullProductsValueTest(){
        //Arr
        checkoutDTO.setEmail("Juan");
        checkoutDTO.setAddressInfo("Home");
        checkoutDTO.setPaymentInfo("Credit Card");
        checkoutDTO.setProducts(null);

        //Act
        Set<ConstraintViolation<CheckoutDTO>> violations = validator.validate(checkoutDTO);

        //Assert
        Assertions.assertThat(violations).hasSize(3);
    }
}

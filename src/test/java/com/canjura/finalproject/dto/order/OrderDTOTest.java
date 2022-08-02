package com.canjura.finalproject.dto.order;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

class OrderDTOTest {

    private static ValidatorFactory validatorFactory;

    private static Validator validator;

    private static final OrderDTO orderDTO = new OrderDTO();

    private static final List<OrderProductDTO> list = new ArrayList<>();

    @BeforeAll
    public static void createValidator(){
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @BeforeEach
    public void createOrderDTOConfig(){
        OrderProductDTO productDTO = new OrderProductDTO();
        productDTO.setName("TV");
        productDTO.setQuantity(1);
        list.add(productDTO);
    }

    @AfterAll
    public static void close(){
        validatorFactory.close();
    }

    @Test
    @DisplayName("No violations")
    void noViolationsTest(){
        //Arr
        orderDTO.setUserInfo("Juan");
        orderDTO.setUseEmail("juan@gmail.com");
        orderDTO.setPaymentInfo("Credit Card");
        orderDTO.setProducts(list);
        orderDTO.setTotal(400.0);
        orderDTO.setDeliveryInfo("Home");

        //Act
        Set<ConstraintViolation<OrderDTO>> violations = validator.validate(orderDTO);

        //Assert
        Assertions.assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Null values")
    void nullValuesTest(){
        //Arr
        orderDTO.setUserInfo(null);
        orderDTO.setUseEmail(null);
        orderDTO.setPaymentInfo(null);
        orderDTO.setProducts(null);
        orderDTO.setTotal(null);
        orderDTO.setDeliveryInfo(null);

        //Act
        Set<ConstraintViolation<OrderDTO>> violations = validator.validate(orderDTO);

        //Assert
        Assertions.assertThat(violations).hasSize(15);
    }

    @Test
    @DisplayName("Null user info")
    void nullUserInfoTest(){
        //Arr
        orderDTO.setUserInfo(null);
        orderDTO.setUseEmail("juan@gmail.com");
        orderDTO.setPaymentInfo("Credit Card");
        orderDTO.setProducts(list);
        orderDTO.setTotal(400.0);
        orderDTO.setDeliveryInfo("Home");

        //Act
        Set<ConstraintViolation<OrderDTO>> violations = validator.validate(orderDTO);

        //Assert
        Assertions.assertThat(violations).hasSize(2);
    }

    @Test
    @DisplayName("Null user email")
    void nullUserEmailTest(){
        //Arr
        orderDTO.setUserInfo("Juan");
        orderDTO.setUseEmail(null);
        orderDTO.setPaymentInfo("Credit Card");
        orderDTO.setProducts(list);
        orderDTO.setTotal(400.0);
        orderDTO.setDeliveryInfo("Home");

        //Act
        Set<ConstraintViolation<OrderDTO>> violations = validator.validate(orderDTO);

        //Assert
        Assertions.assertThat(violations).hasSize(3);
    }

    @Test
    @DisplayName("Null payment info")
    void nullPaymentInfoTest(){
        //Arr
        orderDTO.setUserInfo("Juan");
        orderDTO.setUseEmail("juan@gmail.com");
        orderDTO.setPaymentInfo(null);
        orderDTO.setProducts(list);
        orderDTO.setTotal(400.0);
        orderDTO.setDeliveryInfo("Home");

        //Act
        Set<ConstraintViolation<OrderDTO>> violations = validator.validate(orderDTO);

        //Assert
        Assertions.assertThat(violations).hasSize(3);
    }

    @Test
    @DisplayName("Null products")
    void nullProductsTest(){
        //Arr
        orderDTO.setUserInfo("Juan");
        orderDTO.setUseEmail("juan@gmail.com");
        orderDTO.setPaymentInfo("Credit Card");
        orderDTO.setProducts(null);
        orderDTO.setTotal(400.0);
        orderDTO.setDeliveryInfo("Home");

        //Act
        Set<ConstraintViolation<OrderDTO>> violations = validator.validate(orderDTO);

        //Assert
        Assertions.assertThat(violations).hasSize(2);
    }

    @Test
    @DisplayName("Null total")
    void nullTotalTest(){
        //Arr
        orderDTO.setUserInfo("Juan");
        orderDTO.setUseEmail("juan@gmail.com");
        orderDTO.setPaymentInfo("Credit Card");
        orderDTO.setProducts(list);
        orderDTO.setTotal(null);
        orderDTO.setDeliveryInfo("Home");

        //Act
        Set<ConstraintViolation<OrderDTO>> violations = validator.validate(orderDTO);

        //Assert
        Assertions.assertThat(violations).hasSize(2);
    }

    @Test
    @DisplayName("Null delivery info")
    void nullDeliveryTest(){
        //Arr
        orderDTO.setUserInfo("Juan");
        orderDTO.setUseEmail("juan@gmail.com");
        orderDTO.setPaymentInfo("Credit Card");
        orderDTO.setProducts(list);
        orderDTO.setTotal(400.0);
        orderDTO.setDeliveryInfo(null);

        //Act
        Set<ConstraintViolation<OrderDTO>> violations = validator.validate(orderDTO);

        //Assert
        Assertions.assertThat(violations).hasSize(3);
    }
}

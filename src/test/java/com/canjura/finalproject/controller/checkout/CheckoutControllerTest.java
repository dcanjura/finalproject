package com.canjura.finalproject.controller.checkout;

import com.canjura.finalproject.dto.checkout.CheckoutDTO;
import com.canjura.finalproject.dto.checkout.CheckoutProductDTO;
import com.canjura.finalproject.entity.checkout.Checkout;
import com.canjura.finalproject.entity.checkout.CheckoutProduct;
import com.canjura.finalproject.mapper.checkout.CheckoutStructMapper;
import com.canjura.finalproject.service.checkout.CheckoutService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

class CheckoutControllerTest {
    @InjectMocks
    private CheckoutController controller;

    @Mock
    private CheckoutService service;

    @Mock
    private CheckoutStructMapper mapper;

    private static Checkout checkout = new Checkout();

    private static final CheckoutDTO checkoutDTO = new CheckoutDTO();

    private static final List<CheckoutProductDTO> productsDTO = new ArrayList<>();

    private static List<CheckoutProduct> products = new ArrayList<>();

    private static Validator validator;

    @BeforeAll
    public static void createValidator(){
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @BeforeEach
    public void setConfig(){
        CheckoutProduct product = new CheckoutProduct();
        product.setId(1);
        product.setName("TV");
        product.setQuantity(1);
        products = List.of(product);
        checkout = new Checkout("juan@gmail.com", "Mexico", "Credit Card", products);
        CheckoutProductDTO checkoutProductDTO = new CheckoutProductDTO();
        checkoutProductDTO.setName(product.getName());
        checkoutProductDTO.setQuantity(product.getQuantity());
        productsDTO.add(checkoutProductDTO);
        checkoutDTO.setEmail(checkout.getUserEmail());
        checkoutDTO.setAddressInfo(checkout.getAddressInfo());
        checkoutDTO.setPaymentInfo(checkout.getPaymentInfo());
        checkoutDTO.setProducts(productsDTO);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Get existing checkout process")
    void getCheckoutProcessTest(){
        //Arr
        checkout = new Checkout("juan@gmail.com", "Mexico", "Credit Card", products);

        //Act
        Mockito.when(this.service.getCheckoutInfo("juan@gmail.com")).thenReturn(checkout);
        Mockito.when(this.mapper.checkoutToCheckoutDTO(checkout)).thenReturn(checkoutDTO);

        //Assert
        Assertions.assertThat(this.controller.getCheckout("juan@gmail.com")).isEqualTo(ResponseEntity.status(HttpStatus.FOUND).body(mapper.checkoutToCheckoutDTO(checkout)));
    }

    @Test
    @DisplayName("Get exiting checkout process fail")
    void getCheckoutProcessFailTest(){
        //Arr
        checkout = null;

        //Act
        Mockito.when(this.service.getCheckoutInfo("juan@gmail.com")).thenReturn(checkout);

        //Assert
        org.junit.jupiter.api.Assertions.assertThrows(ResponseStatusException.class, () -> this.controller.getCheckout("juan@gmail.com"));
    }

    @Test
    @DisplayName("Save checkout process")
    void saveCheckoutProcessTest(){
        //Arr
        ResponseEntity<String> response = new ResponseEntity<>("Checkout process saved temporarily", HttpStatus.CREATED);

        //Act
        Mockito.when(this.service.saveCheckoutProcess(checkout)).thenReturn(response);
        Mockito.when(this.mapper.checkoutDTOToCheckout(checkoutDTO)).thenReturn(checkout);

        //Assert
        Assertions.assertThat(this.controller.saveCheckout(checkoutDTO)).isEqualTo(ResponseEntity.status(HttpStatus.CREATED).body("Checkout process saved temporarily"));
    }

    @Test
    @DisplayName("Save checkout process fail")
    void saveCheckoutFailTest(){
        //Arr
        checkoutDTO.setEmail("juan");

        //Act
        Set<ConstraintViolation<CheckoutDTO>> violations = validator.validate(checkoutDTO);
        ConstraintViolation<CheckoutDTO> violation = violations.iterator().next();

        //Assert
        Assertions.assertThat(violations).hasSize(1);
        Assertions.assertThat(violation.getMessage()).hasToString("Email should be a valid one");
        Assertions.assertThat(violation.getPropertyPath()).hasToString("email");
    }

    @Test
    @DisplayName("Update checkout process")
    void updateCheckoutTest(){
        //Arr
        ResponseEntity<String> response = new ResponseEntity<>("Checkout process with user email: "+ checkout.getUserEmail() + " updated", HttpStatus.ACCEPTED);

        //Act
        Mockito.when(this.service.saveCheckoutProcess(checkout)).thenReturn(response);
        Mockito.when(this.mapper.checkoutDTOToCheckout(checkoutDTO)).thenReturn(checkout);

        //Assert
        Assertions.assertThat(this.controller.saveCheckout(checkoutDTO)).isEqualTo(ResponseEntity.status(HttpStatus.ACCEPTED).body("Checkout process with user email: "+ checkout.getUserEmail() + " updated"));
    }

    @Test
    @DisplayName("Delete checkout process by user email")
    void deleteCheckoutByUserEmailTest(){
        //Arr
        ResponseEntity<String> response = new ResponseEntity<>("Checkout process deleted successfully", HttpStatus.ACCEPTED);

        //Act
        Mockito.when(this.service.deleteCheckoutByUserEmail("juan@gmail.com")).thenReturn(response);

        //Assert
        Assertions.assertThat(this.controller.deleteCheckout("juan@gmail.com")).isEqualTo(ResponseEntity.status(HttpStatus.ACCEPTED).body("Checkout process deleted successfully"));
    }

    @Test
    @DisplayName("Delete checkout process by user email fail")
    void deleteCheckoutByUserEmailFailTest(){
        //Arr
        ResponseEntity<String> response = new ResponseEntity<>("Checkout process not found with the given user email", HttpStatus.NOT_FOUND);

        //Act
        Mockito.when(this.service.deleteCheckoutByUserEmail("juan@gmail.com")).thenReturn(response);

        //Assert
        Assertions.assertThat(this.controller.deleteCheckout("juan@gmail.com")).isEqualTo(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Checkout process not found with the given user email"));
    }
}

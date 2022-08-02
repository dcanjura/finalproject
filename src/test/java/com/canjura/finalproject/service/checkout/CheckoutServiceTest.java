package com.canjura.finalproject.service.checkout;

import com.canjura.finalproject.entity.checkout.Checkout;
import com.canjura.finalproject.entity.checkout.CheckoutProduct;
import com.canjura.finalproject.entity.User;
import com.canjura.finalproject.entity.UserAddress;
import com.canjura.finalproject.entity.UserPayment;
import com.canjura.finalproject.repository.checkout.CheckoutRepo;
import com.canjura.finalproject.repository.user.UserRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

class CheckoutServiceTest {
    @InjectMocks
    private CheckoutService service;

    @Mock
    private CheckoutRepo repo;

    @Mock
    private UserRepo userRepo;

    private static Checkout checkout = new Checkout();

    private static User user = new User();

    private static List<CheckoutProduct> products = new ArrayList<>();

    @BeforeEach
    public void checkoutConfig(){
        UserAddress address = new UserAddress("USA", "Home");
        List<UserAddress> addressList = List.of(address);
        UserPayment payment = new UserPayment("516516546841", "Credit Card");
        List<UserPayment> paymentList = List.of(payment);
        user = new User(1, "Juan", "+50378797879", "juan@gmail.com", addressList, paymentList);
        CheckoutProduct product = new CheckoutProduct();
        product.setId(1);
        product.setName("TV");
        product.setQuantity(1);
        products = List.of(product);
        checkout = new Checkout(user.getEmail(), "Home", "Credit Card", products);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Get checkout info")
    void getCheckoutInfoTest(){
        //Arr
        checkout = new Checkout(user.getEmail(), "Home", "Credit Card", products);

        //Act
        Mockito.when(this.repo.findCheckoutByUserEmail(checkout.getUserEmail())).thenReturn(checkout);

        //Assert
        Assertions.assertThat(this.service.getCheckoutInfo(checkout.getUserEmail())).isNotNull();
    }

    @Test
    @DisplayName("Get checkout info fail")
    void getCheckoutInfoFailTest(){
        //Arr
        checkout = null;

        //Act
        Mockito.when(this.repo.findCheckoutByUserEmail("jorge@gmail.com")).thenReturn(checkout);

        //Assert
        Assertions.assertThat(this.service.getCheckoutInfo("jorge@gmail.com")).isNull();
    }

    @Test
    @DisplayName("Save checkout process")
    void saveCheckoutProcessTest(){
        //Arr
        ResponseEntity<String> response = new ResponseEntity<>("Checkout process saved temporarily", HttpStatus.CREATED);

        //Act
        Mockito.when(this.userRepo.findUserByEmail(checkout.getUserEmail())).thenReturn(user);

        //Assert
        Assertions.assertThat(this.service.saveCheckoutProcess(checkout)).isEqualTo(response);
    }

    @Test
    @DisplayName("Save checkout user info not found")
    void saveCheckoutUserNotFoundFailTest(){
        //Arr
        user = null;

        //Act
        Mockito.when(this.userRepo.findUserByEmail("jorge@gmail.com")).thenReturn(user);

        //Assert
        Assertions.assertThat(this.service.saveCheckoutProcess(checkout)).isEqualTo(ResponseEntity.status(HttpStatus.NOT_FOUND).body("We could not find a user with the given email"));
    }

    @Test
    @DisplayName("Update checkout process")
    void updateCheckoutProcessTest(){
        //Arr
        ResponseEntity<String> response = new ResponseEntity<>("Checkout process with user email: "+ checkout.getUserEmail() + " updated", HttpStatus.ACCEPTED);

        //Act
        Mockito.when(this.repo.findCheckoutByUserEmail(checkout.getUserEmail())).thenReturn(checkout);

        //Assert
        Assertions.assertThat(this.service.saveCheckoutProcess(checkout)).isEqualTo(response);
    }

    @Test
    @DisplayName("Update checkout 0 products")
    void updateCheckoutNoProductsFailTest(){
        //Arr
        checkout.setProducts(null);

        //Act
        Mockito.when(this.repo.findCheckoutByUserEmail(checkout.getUserEmail())).thenReturn(checkout);

        //Assert
        Assertions.assertThat(this.service.saveCheckoutProcess(checkout)).isEqualTo(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Products went to 0, checkout process was deleted"));
    }

    @Test
    @DisplayName("Delete checkout process")
    void deleteCheckoutProcessTest(){
        //Arr
        ResponseEntity<String> response = new ResponseEntity<>("Checkout process deleted successfully", HttpStatus.ACCEPTED);

        //Act
        Mockito.when(this.repo.findCheckoutByUserEmail(checkout.getUserEmail())).thenReturn(checkout);

        //Assert
        Assertions.assertThat(this.service.deleteCheckoutByUserEmail(checkout.getUserEmail())).isEqualTo(response);
    }

    @Test
    @DisplayName("Delete checkout process fail")
    void deleteCheckoutProcessFailTest(){
        //Arr
        checkout = null;

        //Act
        Mockito.when(this.repo.findCheckoutByUserEmail("jorge@gmail.com")).thenReturn(checkout);

        //Assert
        Assertions.assertThat(this.service.deleteCheckoutByUserEmail("jorge@gmail.com")).isEqualTo(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Checkout process not found with the given user email"));
    }
}

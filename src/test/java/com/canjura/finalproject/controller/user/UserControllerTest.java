package com.canjura.finalproject.controller.user;

import com.canjura.finalproject.entity.User;
import com.canjura.finalproject.entity.UserAddress;
import com.canjura.finalproject.entity.UserPayment;
import com.canjura.finalproject.service.user.UserService;
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

class UserControllerTest {
    @InjectMocks
    private UserController controller;

    @Mock
    private UserService service;

    private static List<UserAddress> addresses = new ArrayList<>();

    private static List<UserPayment> payments = new ArrayList<>();

    private User user = new User();

    @BeforeEach
    void createConfig(){
        MockitoAnnotations.initMocks(this);
        UserAddress address = new UserAddress("Mejicanos", "Home");
        address.setId(1);
        addresses = List.of(address);
        UserPayment payment = new UserPayment("juan@gmail.com", "PayPal");
        payments = List.of(payment);
        user = new User(1, "Juan Perez", "+50378797879", "juan@gmail.com", addresses, payments);
    }

    @Test
    @DisplayName("Get User Info")
    void getUserInfoTest(){
        //Arr
        ResponseEntity<String> message = new ResponseEntity<>("User info:" + user.getName() + " " + user.getPhone() + " addresses info: " + addresses + " payments info: " + payments, HttpStatus.FOUND);

        //Act
        Mockito.when(this.service.getUserInfo(user.getEmail())).thenReturn(message);

        //Assert
        Assertions.assertThat(this.controller.getUserBy(user.getEmail())).isEqualTo(message);
    }

    @Test
    @DisplayName("Get User Info Fail")
    void getUserInfoFailTest(){
        //Arr
        ResponseEntity<String> message = new ResponseEntity<>("We could not find a user with the given email", HttpStatus.NOT_FOUND);

        //Act
        Mockito.when(this.service.getUserInfo(user.getEmail())).thenReturn(message);

        //Assert
        Assertions.assertThat(this.controller.getUserBy(user.getEmail())).isEqualTo(message);
    }
}

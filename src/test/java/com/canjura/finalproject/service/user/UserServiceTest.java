package com.canjura.finalproject.service.user;

import com.canjura.finalproject.entity.User;
import com.canjura.finalproject.entity.UserAddress;
import com.canjura.finalproject.entity.UserPayment;
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
import java.util.List;

class UserServiceTest {
    @InjectMocks
    private UserService service;

    @Mock
    private UserRepo repo;

    private static User user = new User();

    @BeforeEach
    public void userConfig(){
        UserAddress address = new UserAddress("Mexico", "Home");
        List<UserAddress> addressList = List.of(address);
        UserPayment payment = new UserPayment("juan@gmail.com", "Paypal");
        List<UserPayment> paymentList = List.of(payment);
        user = new User(1, "Juan", "+50378797879", "juan@gmail.com", addressList, paymentList);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Get user info")
    void getUserInfoTest(){
        //Arr
        List<String> addresses;
        List<String> payments;

        //Act
        Mockito.when(this.repo.findUserByEmail("juan@gmail.com")).thenReturn(user);
        addresses = this.service.getAddresses(user.getAddress());
        payments = this.service.getPayments(user.getPayments());

        //Assert
        Assertions.assertThat(this.service.getUserInfo("juan@gmail.com")).isEqualTo(ResponseEntity.status(HttpStatus.FOUND).body("User info:" + user.getName() + " " + user.getPhone() + " addresses info: " + addresses + " payments info: " + payments));
    }

    @Test
    @DisplayName("Get user info Fail")
    void getUserInfoFailTest(){
        //Act
        user = null;

        //Act
        Mockito.when(this.repo.findUserByEmail("jorge@gmail.com")).thenReturn(user);

        //Assert
        Assertions.assertThat(this.service.getUserInfo("jorge@gmail.com")).isEqualTo(ResponseEntity.status(HttpStatus.NOT_FOUND).body("We could not find a user with the given email"));
    }
}

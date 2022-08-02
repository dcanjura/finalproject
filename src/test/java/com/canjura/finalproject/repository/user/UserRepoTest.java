package com.canjura.finalproject.repository.user;

import com.canjura.finalproject.entity.User;
import com.canjura.finalproject.entity.UserAddress;
import com.canjura.finalproject.entity.UserPayment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepoTest {

    @Autowired
    private UserRepo repo;

    private static final User user = new User();

    @BeforeEach
    public void createConfig(){
        UserAddress address = new UserAddress("Mexico", "Home");
        List<UserAddress> addressList = List.of(address);
        UserPayment payment = new UserPayment("juan@gmail.com", "paypal");
        List<UserPayment> paymentList = List.of(payment);
        user.setName("Juan");
        user.setPhone("+50378797879");
        user.setEmail("juna@gmail.com");
        user.setAddress(addressList);
        user.setPayments(paymentList);
    }

    @Container
    public static MySQLContainer mySQLContainer = new MySQLContainer<>("mysql:5.7")
            .withDatabaseName("integration-final-project")
            .withPassword("user")
            .withUsername("password");

    @DynamicPropertySource
    public static void properties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
    }

    @Test
    @DisplayName("Get user info test")
    void getUserInfoTest(){
        //Arr
        this.repo.save(user);

        //Act
        User temp = this.repo.findUserByEmail(user.getEmail());

        //Assert
        Assertions.assertThat(temp).isNotNull();
    }
}

package com.canjura.finalproject.repository.order;

import com.canjura.finalproject.entity.order.Order;
import com.canjura.finalproject.entity.order.OrderProduct;
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
class OrderRepoTest {

    @Autowired
    private OrderRepo repo;

    private static final Order order = new Order();

    @BeforeEach
    public void createConfig(){
        OrderProduct product = new OrderProduct();
        product.setId(1);
        product.setName("TV");
        product.setQuantity(2);
        List<OrderProduct> products = List.of(product);
        order.setId(1);
        order.setUserInfo("Juan");
        order.setUserEmail("juan@gmail.com");
        order.setPaymentInfo("paypal");
        order.setOrderProducts(products);
        order.setTotal(600.00);
        order.setDeliveryInfo("home");
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
    @DisplayName("Save order")
    void saveOrderTest(){
        //Arr
        Order test;

        //Act
        test = this.repo.save(order);

        //Assert
        Assertions.assertThat(test).isNotNull();
    }

    @Test
    @DisplayName("Get order history")
    void getOrderHistoryTest(){
        //Arr
        List<Order> orders;

        //Act
        this.repo.save(order);
        orders = this.repo.getAllByUserEmail(order.getUserEmail());

        //Assert
        Assertions.assertThat(orders).isNotNull();
    }
}

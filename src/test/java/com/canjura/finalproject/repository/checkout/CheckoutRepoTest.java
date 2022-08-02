package com.canjura.finalproject.repository.checkout;

import com.canjura.finalproject.entity.checkout.Checkout;
import com.canjura.finalproject.entity.checkout.CheckoutProduct;
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
class CheckoutRepoTest {
    @Autowired
    private CheckoutRepo repo;

    private static Checkout checkout = new Checkout();

    @BeforeEach
    public void createConfig(){
        CheckoutProduct product = new CheckoutProduct();
        product.setName("TV");
        product.setQuantity(2);
        List<CheckoutProduct> products = List.of(product);
        checkout = new Checkout("juan@gmail.com", "Home", "Paypal", products);
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
    @DisplayName("Save order process")
    void saveCheckoutProcessTest(){
        //Arr
        Checkout temp;

        //Act
        temp = repo.save(checkout);

        //Assert
        Assertions.assertThat(temp).isNotNull();
    }

    @Test
    @DisplayName("Delete order process")
    void deleteCheckoutProcessTest(){
        //Arr
        repo.save(checkout);

        //Act
        repo.deleteCheckoutByUserEmail(checkout.getUserEmail());

        //Assert
        Assertions.assertThat(repo.findCheckoutByUserEmail(checkout.getUserEmail())).isNull();
    }
}

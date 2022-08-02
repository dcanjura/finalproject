package com.canjura.finalproject.repository.product;

import com.canjura.finalproject.entity.product.Product;
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

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepoTest {
    @Autowired
    private ProductRepo repo;

    private static final Product product = new Product();

    @BeforeEach
    public void createConfig(){
        product.setId(1);
        product.setName("TV");
        product.setStock(20);
        product.setPrice(255.00);
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
    @DisplayName("Get products list")
    void getProductsTest(){
        //Arr
        this.repo.save(product);

        //Act
        Product test = this.repo.findProductByName(product.getName());

        //Assert
        Assertions.assertThat(test).isNotNull();
    }
}

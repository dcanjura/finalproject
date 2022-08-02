package com.canjura.finalproject.service.product;

import com.canjura.finalproject.entity.product.Product;
import com.canjura.finalproject.repository.product.ProductRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

class ProductServiceTest {
    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepo repo;

    private static List<Product> products = new ArrayList<>();

    private static final Product product = new Product();

    @BeforeEach
    public void productConfig(){
        product.setId(1);
        product.setName("TV");
        product.setStock(20);
        product.setPrice(450.00);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Get all products")
    void getAllProductsTest(){
        //Arr
        products.add(product);

        //Act
        Mockito.when(this.repo.findAll()).thenReturn(products);

        //Assert
        Assertions.assertThat(this.service.getProducts()).isNotNull();
    }

    @Test
    @DisplayName("Get all products fail")
    void getAllProductsFailTest(){
        //Arr
        products = new ArrayList<>();

        //Act
        Mockito.when(this.repo.findAll()).thenReturn(products);

        //Assert
        Assertions.assertThat(this.service.getProducts()).isEmpty();
    }
}

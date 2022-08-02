package com.canjura.finalproject.mapper.checkout;

import com.canjura.finalproject.dto.checkout.CheckoutDTO;
import com.canjura.finalproject.dto.checkout.CheckoutProductDTO;
import com.canjura.finalproject.entity.checkout.Checkout;
import com.canjura.finalproject.entity.checkout.CheckoutProduct;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.List;

class CheckoutMapperTest {

    private final CheckoutMapperImpl mapper = new CheckoutMapperImpl();

    private static final Checkout checkout = new Checkout();

    private static final CheckoutDTO checkoutDTO = new CheckoutDTO();

    @BeforeEach
    public void createConfig(){
        CheckoutProduct product = new CheckoutProduct();
        product.setId(1);
        product.setName("TV");
        product.setQuantity(1);
        List<CheckoutProduct> products = List.of(product);
        checkout.setId(1);
        checkout.setUserEmail("juan@gmail.com");
        checkout.setAddressInfo("Home");
        checkout.setPaymentInfo("credit card");
        checkout.setProducts(products);
        CheckoutProductDTO productDTO = new CheckoutProductDTO();
        productDTO.setName("TV");
        productDTO.setQuantity(1);
        List<CheckoutProductDTO> productDTOS = List.of(productDTO);
        checkoutDTO.setEmail("juan@gmail.com");
        checkoutDTO.setAddressInfo("Home");
        checkoutDTO.setPaymentInfo("Credit Card");
        checkoutDTO.setProducts(productDTOS);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Mapper checkout to checkoutDTO")
    void checkoutToCheckoutDTOTest(){
        //Arr
        CheckoutDTO test;

        //Act
        test = this.mapper.checkoutToCheckoutDTO(checkout);

        //Assert
        Assertions.assertThat(test).isNotNull();
    }

    @Test
    @DisplayName("Mapper null checkout to checkoutDTO")
    void nullCheckoutToCheckoutDTOTest(){
        //Arr
        CheckoutDTO test;

        //Act
        test = this.mapper.checkoutToCheckoutDTO(null);

        //Assert
        Assertions.assertThat(test).isNull();
    }

    @Test
    @DisplayName("Mapper checkoutDTO to checkout")
    void checkoutDTOToCheckout(){
        //Arr
        Checkout test;

        //Act
        test = this.mapper.checkoutDTOToCheckout(checkoutDTO);

        //Assert
        Assertions.assertThat(test).isNotNull();
    }

    @Test
    @DisplayName("Mapper null checkoutDTO to checkout")
    void nullCheckoutDTOToCheckoutTest(){
        //Arr
        Checkout test;

        //Act
        test = this.mapper.checkoutDTOToCheckout(null);

        //Assert
        Assertions.assertThat(test).isNull();
    }


}

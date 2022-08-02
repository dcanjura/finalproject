package com.canjura.finalproject.mapper.order;

import com.canjura.finalproject.dto.order.OrderDTO;
import com.canjura.finalproject.dto.order.OrderProductDTO;
import com.canjura.finalproject.entity.order.Order;
import com.canjura.finalproject.entity.order.OrderProduct;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class OrderMapperTest {

    private final OrderMapperImpl mapper = new OrderMapperImpl();

    private static Order order = new Order();

    private static final OrderDTO orderDTO = new OrderDTO();

    @BeforeEach
    public void createConfig(){
        OrderProduct product = new OrderProduct();
        product.setId(1);
        product.setName("TV");
        product.setQuantity(1);
        List<OrderProduct> products = List.of(product);
        order = new Order("Juan", "juan@gmail.com", "Paypal", products, 400.00, "Home");
        OrderProductDTO productDTO = new OrderProductDTO();
        productDTO.setName("TV");
        productDTO.setQuantity(1);
        List<OrderProductDTO> productDTOS = List.of(productDTO);
        orderDTO.setUserInfo("Juan");
        orderDTO.setUseEmail("juan@gmail.com");
        orderDTO.setPaymentInfo("Paypal");
        orderDTO.setProducts(productDTOS);
        orderDTO.setTotal(400.00);
        orderDTO.setDeliveryInfo("Home");
    }

    @Test
    @DisplayName("Mapper orders to orderDTOs")
    void ordersToOrderDTOsTest(){
        //Arr
        List<OrderDTO> test;
        List<Order> orders = List.of(order);

        //Act
        test = mapper.orderToOrderDTO(orders);

        //Assert
        Assertions.assertThat(test).isNotNull();
    }

    @Test
    @DisplayName("Mapper null orders to orderDTOs")
    void nullOrdersToOrderDTOsTest(){
        //Arr
        List<OrderDTO> test;
        List<Order> orders = new ArrayList<>();

        //Act
        test = mapper.orderToOrderDTO(orders);

        //Assert
        Assertions.assertThat(test).isNull();
    }
}

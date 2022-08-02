package com.canjura.finalproject.controller.order;

import com.canjura.finalproject.dto.order.OrderDTO;
import com.canjura.finalproject.dto.order.OrderProductDTO;
import com.canjura.finalproject.entity.order.Order;
import com.canjura.finalproject.entity.order.OrderProduct;
import com.canjura.finalproject.mapper.order.OrderStructMapper;
import com.canjura.finalproject.service.order.OrderService;
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
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;

class OrderControllerTest {
    @InjectMocks
    private OrderController controller;

    @Mock
    private OrderService service;

    @Mock
    private OrderStructMapper mapper;

    private static Order order = new Order();

    private static final OrderDTO orderDTO = new OrderDTO();

    private static final List<OrderProductDTO> productsDTO = new ArrayList<>();

    private static List<Order> orders = new ArrayList<>();

    @BeforeEach
    void setConfig(){
        OrderProduct product = new OrderProduct();
        product.setName("TV");
        product.setQuantity(1);
        List<OrderProduct> products = List.of(product);
        order = new Order("Juan", "juan@gmail.com", "Paypal", products, 2.0, "Mexico");
        order.setId(1);
        orders.add(order);
        OrderProductDTO orderProductDTO = new OrderProductDTO();
        orderProductDTO.setName(product.getName());
        orderProductDTO.setQuantity(product.getQuantity());
        productsDTO.add(orderProductDTO);
        orderDTO.setUserInfo(order.getUserInfo());
        orderDTO.setUseEmail(order.getUserEmail());
        orderDTO.setPaymentInfo(order.getPaymentInfo());
        orderDTO.setProducts(productsDTO);
        orderDTO.setTotal(order.getTotal());
        orderDTO.setDeliveryInfo(order.getDeliveryInfo());
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Get user's order history")
    void getOrderHistoryTest(){
        //Arr
        List<OrderDTO> orderDTOS = List.of(orderDTO);

        //Act
        Mockito.when(this.service.getOrders(order.getUserEmail())).thenReturn(orders);
        Mockito.when(this.mapper.orderToOrderDTO(orders)).thenReturn(orderDTOS);

        //Assert
        Assertions.assertThat(this.controller.getOrders(orderDTO.getUseEmail())).isEqualTo(ResponseEntity.status(HttpStatus.FOUND).body(mapper.orderToOrderDTO(orders)));
    }

    @Test
    @DisplayName("Get user's order history fail")
    void getOrderHistoryFailTest(){
        //Arr
        orders = new ArrayList<>();

        //Act
        Mockito.when(this.service.getOrders(order.getUserEmail())).thenReturn(orders);

        //Assert
        org.junit.jupiter.api.Assertions.assertThrows(ResponseStatusException.class, () -> this.controller.getOrders("jorge@gmail.com"));
    }

    @Test
    @DisplayName("Process order")
    void processOrderTest(){
        //Arr
        ResponseEntity<String> response = new ResponseEntity<>("Order processed successfully", HttpStatus.CREATED);

        //Act
        Mockito.when(this.service.createOrder("juan@gmail.com")).thenReturn(ResponseEntity.status(HttpStatus.CREATED).body("Order processed successfully"));

        //Assert
        Assertions.assertThat(this.controller.saveOrderProcess("juan@gmail.com")).isEqualTo(response);
    }

    @Test
    @DisplayName("Process order fail")
    void processOrderFailTest(){
        //Arr
        ResponseEntity<String> response = new ResponseEntity<>("No checkout process found with the given user email", HttpStatus.NOT_FOUND);

        //Act
        Mockito.when(this.service.createOrder("jorge@gmail.com")).thenReturn(response);

        //Assert
        Assertions.assertThat(this.controller.saveOrderProcess("jorge@gmail.com")).isEqualTo(ResponseEntity.status(HttpStatus.NOT_FOUND).body("No checkout process found with the given user email"));
    }
}

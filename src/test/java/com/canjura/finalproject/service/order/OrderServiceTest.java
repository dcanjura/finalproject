package com.canjura.finalproject.service.order;

import com.canjura.finalproject.entity.checkout.Checkout;
import com.canjura.finalproject.entity.checkout.CheckoutProduct;
import com.canjura.finalproject.entity.order.Order;
import com.canjura.finalproject.entity.order.OrderProduct;
import com.canjura.finalproject.entity.product.Product;
import com.canjura.finalproject.entity.User;
import com.canjura.finalproject.entity.UserAddress;
import com.canjura.finalproject.entity.UserPayment;
import com.canjura.finalproject.repository.checkout.CheckoutRepo;
import com.canjura.finalproject.repository.order.OrderRepo;
import com.canjura.finalproject.repository.product.ProductRepo;
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

import java.util.ArrayList;
import java.util.List;

class OrderServiceTest {
    @InjectMocks
    private OrderService service;

    @Mock
    private OrderRepo repo;

    @Mock
    private UserRepo userRepo;

    @Mock
    private ProductRepo productRepo;

    @Mock
    private CheckoutRepo checkoutRepo;

    private Order order;

    private static Checkout checkout = new Checkout();

    private static User user = new User();

    private static OrderProduct orderProduct = new OrderProduct();

    private static final Product product = new Product();

    private static List<CheckoutProduct> checkoutProducts = new ArrayList<>();

    private static CheckoutProduct checkoutProduct = new CheckoutProduct();

    private static UserAddress address = new UserAddress();

    private static UserPayment payment = new UserPayment();

    @BeforeEach
    public void orderConfig(){
        address = new UserAddress("USA", "Home");
        List<UserAddress> addresses = List.of(address);
        payment = new UserPayment("juan@gmail.com", "Paypal");
        List<UserPayment> payments = List.of(payment);
        user = new User(1, "Juan Perez", "+50378797879", "juan@gmail.com", addresses, payments);
        checkoutProduct = new CheckoutProduct();
        checkoutProduct.setId(1);
        checkoutProduct.setName("TV");
        checkoutProduct.setQuantity(1);
        checkoutProducts = List.of(checkoutProduct);
        checkout = new Checkout("juan@gmail.com", "Home", "Paypal", checkoutProducts);
        checkout.setId(1);
        orderProduct = new OrderProduct();
        orderProduct.setId(checkoutProduct.getId());
        orderProduct.setName(checkoutProduct.getName());
        orderProduct.setQuantity(checkoutProduct.getQuantity());
        List<OrderProduct> products = List.of(orderProduct);
        order = new Order("Juan Perez", "juan@gmail.com", "Paypal", products, 300.00, "USA");
        product.setId(1);
        product.setName("TV");
        product.setStock(20);
        product.setPrice(120.00);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Save order")
    void saveOrderTest(){
        //Arr
        ResponseEntity<String> response = new ResponseEntity<>("Order processed successfully", HttpStatus.CREATED);

        //Act
        Mockito.when(this.repo.save(order)).thenReturn(order);
        Mockito.when(this.userRepo.findUserByEmail(checkout.getUserEmail())).thenReturn(user);
        Mockito.when(this.productRepo.findProductByName(orderProduct.getName())).thenReturn(product);
        Mockito.when(this.checkoutRepo.findCheckoutByUserEmail(user.getEmail())).thenReturn(checkout);

        //Assert
        Assertions.assertThat(this.service.createOrder("juan@gmail.com")).isEqualTo(response);
    }

    @Test
    @DisplayName("Save order out of stock fail")
    void outOfSockFailTest(){
        //Arr
        product.setStock(0);

        //Act
        Mockito.when(this.repo.save(order)).thenReturn(order);
        Mockito.when(this.userRepo.findUserByEmail(checkout.getUserEmail())).thenReturn(user);
        Mockito.when(this.productRepo.findProductByName(orderProduct.getName())).thenReturn(product);
        Mockito.when(this.checkoutRepo.findCheckoutByUserEmail(user.getEmail())).thenReturn(checkout);
        List<String> list = this.service.isLower(checkoutProducts);

        //Arr
        Assertions.assertThat(this.service.createOrder(checkout.getUserEmail())).isEqualTo(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(list.toString()));
    }

    @Test
    @DisplayName("Save order product quantity higher than stock fail")
    void quantityHigherThanStockFailTest(){
        //Arr
        checkoutProduct.setQuantity(30);

        //Act
        Mockito.when(this.repo.save(order)).thenReturn(order);
        Mockito.when(this.userRepo.findUserByEmail(checkout.getUserEmail())).thenReturn(user);
        Mockito.when(this.productRepo.findProductByName(orderProduct.getName())).thenReturn(product);
        Mockito.when(this.checkoutRepo.findCheckoutByUserEmail(user.getEmail())).thenReturn(checkout);
        List<String> list = this.service.isLower(checkoutProducts);

        //Arr
        Assertions.assertThat(this.service.createOrder(checkout.getUserEmail())).isEqualTo(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(list.toString()));
    }

    @Test
    @DisplayName("Save order payment and/or address not found")
    void paymentAndOrAddressFailTest(){
        //Arr
        checkout.setAddressInfo("work");
        checkout.setPaymentInfo("credit card");

        //Act
        Mockito.when(this.repo.save(order)).thenReturn(order);
        Mockito.when(this.userRepo.findUserByEmail(checkout.getUserEmail())).thenReturn(user);
        Mockito.when(this.productRepo.findProductByName(orderProduct.getName())).thenReturn(product);
        Mockito.when(this.checkoutRepo.findCheckoutByUserEmail(user.getEmail())).thenReturn(checkout);

        //Arr
        Assertions.assertThat(this.service.createOrder(checkout.getUserEmail())).isEqualTo(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Address and/or Payment info not found"));
    }

    @Test
    @DisplayName("Save order payment and/or address not found 2")
    void paymentAndOrAddressFailSecondTest(){
        //Arr
        address = new UserAddress("Mexico", "Work");
        payment = new UserPayment("614651984961", "Credit Card");
        List<UserAddress> addresses = List.of(address);
        List<UserPayment> payments = List.of(payment);
        user.setAddress(addresses);
        user.setPayments(payments);
        checkout.setAddressInfo("home");
        checkout.setPaymentInfo("paypal");

        //Act
        Mockito.when(this.repo.save(order)).thenReturn(order);
        Mockito.when(this.userRepo.findUserByEmail(checkout.getUserEmail())).thenReturn(user);
        Mockito.when(this.productRepo.findProductByName(orderProduct.getName())).thenReturn(product);
        Mockito.when(this.checkoutRepo.findCheckoutByUserEmail(user.getEmail())).thenReturn(checkout);

        //Arr
        Assertions.assertThat(this.service.createOrder(checkout.getUserEmail())).isEqualTo(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Address and/or Payment info not found"));
    }

    @Test
    @DisplayName("Save order no checkout process found")
    void checkoutProcessNotFoundFailTest(){
        //Arr
        checkout = null;

        //Act
        Mockito.when(this.checkoutRepo.findCheckoutByUserEmail("jorge@gmail.com")).thenReturn(checkout);

        //Assert
        Assertions.assertThat(this.service.createOrder("jorge@gmail.com")).isEqualTo(ResponseEntity.status(HttpStatus.NOT_FOUND).body("No checkout process found with the given user email"));
    }

    @Test
    @DisplayName("Get user's order history")
    void getOrderHistoryTest(){
        //Arr
        List<Order> orders = List.of(order);

        //Act
        Mockito.when(this.repo.getAllByUserEmail("juan@gmail.com")).thenReturn(orders);

        //Assert
        Assertions.assertThat(this.service.getOrders("juan@gmail.com")).isNotNull();
    }

    @Test
    @DisplayName("Get user's order history fail")
    void getOrderHistoryFailTest(){
        //Arr
        List<Order> orders = new ArrayList<>();

        //Act
        Mockito.when(this.repo.getAllByUserEmail("juan@gmail.com")).thenReturn(orders);

        //Assert
        Assertions.assertThat(this.service.getOrders("juan@gmail.com")).isEmpty();
    }
}

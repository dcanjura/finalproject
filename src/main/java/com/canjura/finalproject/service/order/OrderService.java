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
import com.canjura.finalproject.strategies.address.AddressStrategy;
import com.canjura.finalproject.strategies.address.HomeAddress;
import com.canjura.finalproject.strategies.address.WorkAddress;
import com.canjura.finalproject.strategies.payment.PayByCreditCard;
import com.canjura.finalproject.strategies.payment.PayByPayPal;
import com.canjura.finalproject.strategies.payment.PayStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private CheckoutRepo checkoutRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ProductRepo productRepo;

    public ResponseEntity<String> createOrder(String email){
        Checkout checkout = checkoutRepo.findCheckoutByUserEmail(email);
        if(checkout != null){
            if(isLower(checkout.getProducts()).isEmpty()){
                User user = userRepo.findUserByEmail(checkout.getUserEmail());
                AddressStrategy addressStrategy = getAddressStrategy(checkout.getAddressInfo());
                UserAddress address = addressStrategy.getAddress(user, checkout.getAddressInfo());
                PayStrategy payStrategy = getPaymentStrategy(checkout.getPaymentInfo());
                UserPayment payment = payStrategy.getPayment(user, checkout.getPaymentInfo());
                if(address != null  && payment != null){
                    Order order = new Order();
                    order.setUserInfo(user.getName() + " " + user.getPhone());
                    order.setUserEmail(user.getEmail());
                    order.setPaymentInfo(payment.getType());
                    order.setOrderProducts(createOrderList(checkout.getProducts()));
                    order.setTotal(getTotalAmount(checkout));
                    order.setDeliveryInfo(address.getType() + ": " + address.getAddress());
                    orderRepo.save(order);
                    updateProducts(order.getOrderProducts());
                    checkoutRepo.deleteCheckoutByUserEmail(checkout.getUserEmail());

                    return ResponseEntity.status(HttpStatus.CREATED).body("Order processed successfully");
                }else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Address and/or Payment info not found");
                }
            }else {
                List<String> list = isLower(checkout.getProducts());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(list.toString());
            }
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No checkout process found with the given user email");
        }
    }

    public List<Order> getOrders(String email){
        return orderRepo.getAllByUserEmail(email);
    }

    public AddressStrategy getAddressStrategy(String type){
        if(type.equalsIgnoreCase("home")){
            return new HomeAddress();
        }else{
            return new WorkAddress();
        }
    }

    public PayStrategy getPaymentStrategy(String type){
        if(type.equalsIgnoreCase("credit card")){
            return new PayByCreditCard();
        }else{
            return new PayByPayPal();
        }
    }

    public Double getTotalAmount(Checkout checkout){
        double total = 0.0;
        List<CheckoutProduct> products = checkout.getProducts();
        for(CheckoutProduct product : products){
            Product tempProduct = productRepo.findProductByName(product.getName());
            total += product.getQuantity() * tempProduct.getPrice();
        }
        return total;
    }

    public List<String> isLower(List<CheckoutProduct> checkoutProducts){
        List<String> higherList = new ArrayList<>();
        for(CheckoutProduct product : checkoutProducts){
            Product tempProduct = productRepo.findProductByName(product.getName());
            if(tempProduct.getStock() < 1){
                higherList.add(tempProduct.getName() + " is out of stock");
            }else {
                if(product.getQuantity() > tempProduct.getStock()){
                    higherList.add(tempProduct.getName() + ": stock is lower than the entered quantity");
                }
            }
        }
        return higherList;
    }

    public List<OrderProduct> createOrderList(List<CheckoutProduct> products){
        List<OrderProduct> list = new ArrayList<>();
        for(CheckoutProduct checkoutProduct: products){
            OrderProduct temp = new OrderProduct();
            temp.setName(checkoutProduct.getName());
            temp.setQuantity(checkoutProduct.getQuantity());
            list.add(temp);
        }
        return list;
    }

    public void updateProducts(List<OrderProduct> products){
        for(OrderProduct product : products){
            Product temp = productRepo.findProductByName(product.getName());
            temp.setStock(temp.getStock() - product.getQuantity());
            productRepo.save(temp);
        }
    }
}

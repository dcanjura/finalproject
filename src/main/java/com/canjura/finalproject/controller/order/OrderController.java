package com.canjura.finalproject.controller.order;

import com.canjura.finalproject.dto.order.OrderDTO;
import com.canjura.finalproject.mapper.order.OrderStructMapper;
import com.canjura.finalproject.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("orders/")
public class OrderController {
    @Autowired
    private OrderService service;

    @Autowired
    private OrderStructMapper orderMapper;

    @GetMapping("get/orders/by")
    public ResponseEntity<List<OrderDTO>> getOrders(@RequestParam String email){
        if(!service.getOrders(email).isEmpty()){
            List<OrderDTO> orders = orderMapper.orderToOrderDTO(service.getOrders(email));
            return ResponseEntity.status(HttpStatus.FOUND).body(orders);
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No orders found with the given user email");
        }
    }

    @PostMapping("save/order")
    public ResponseEntity<String> saveOrderProcess(@RequestParam String email){
        return service.createOrder(email);
    }
}

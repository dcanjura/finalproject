package com.canjura.finalproject.mapper.order;

import com.canjura.finalproject.dto.order.OrderDTO;
import com.canjura.finalproject.dto.order.OrderProductDTO;
import com.canjura.finalproject.entity.order.Order;
import com.canjura.finalproject.entity.order.OrderProduct;
import com.canjura.finalproject.mapper.checkout.CheckoutStructMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.processing.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated(value = "org.mapstruct.ap.MappingProcessor")
@Component
public class OrderMapperImpl implements OrderStructMapper{
    @Autowired
    private CheckoutStructMapper checkoutStructMapper;

    @Override
    public List<OrderDTO> orderToOrderDTO(List<Order> orders) {
        if(orders.isEmpty()){
            return null;
        }

        List<OrderDTO> orderDTOS = new ArrayList<>();

        for(Order order : orders){
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setUserInfo(order.getUserInfo());
            orderDTO.setUseEmail(order.getUserEmail());
            orderDTO.setPaymentInfo(order.getPaymentInfo());
            orderDTO.setProducts(orderProductListToOrderProductDTOList(order.getOrderProducts()));
            orderDTO.setTotal(order.getTotal());
            orderDTO.setDeliveryInfo(order.getDeliveryInfo());
            orderDTOS.add(orderDTO);
        }
        return orderDTOS;
    }

    private List<OrderProductDTO> orderProductListToOrderProductDTOList(List<OrderProduct> orderProducts){
        if(orderProducts.isEmpty()){
            return null;
        }

        List<OrderProductDTO> list = new ArrayList<>();
        for(OrderProduct orderProduct : orderProducts){
            list.add(orderProductToOrderProductDTO(orderProduct));
        }
        return list;
    }

    private OrderProductDTO orderProductToOrderProductDTO(OrderProduct product){
        if(product == null){
            return null;
        }

        OrderProductDTO orderProductDTO = new OrderProductDTO();

        orderProductDTO.setName(product.getName());
        orderProductDTO.setQuantity(product.getQuantity());
        return orderProductDTO;
    }
}

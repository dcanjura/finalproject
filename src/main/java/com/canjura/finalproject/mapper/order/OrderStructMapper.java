package com.canjura.finalproject.mapper.order;

import com.canjura.finalproject.dto.order.OrderDTO;
import com.canjura.finalproject.entity.order.Order;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderStructMapper {
    List<OrderDTO> orderToOrderDTO(List<Order> orders);
}

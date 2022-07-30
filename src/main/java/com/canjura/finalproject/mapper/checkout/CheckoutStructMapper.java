package com.canjura.finalproject.mapper.checkout;

import com.canjura.finalproject.dto.checkout.CheckoutDTO;
import com.canjura.finalproject.dto.checkout.CheckoutProductDTO;
import com.canjura.finalproject.entity.checkout.Checkout;
import com.canjura.finalproject.entity.checkout.CheckoutProduct;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CheckoutStructMapper {
    CheckoutDTO checkoutToCheckoutDTO(Checkout checkout);

    Checkout checkoutDTOToCheckout(CheckoutDTO checkoutDTO);

    CheckoutProductDTO checkoutProductToCheckoutProductDTO(CheckoutProduct checkoutProduct);

    CheckoutProduct checkoutProductDTOtoCheckoutProduct(CheckoutProductDTO checkoutProductDTO);
}

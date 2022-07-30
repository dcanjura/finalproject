package com.canjura.finalproject.mapper.checkout;

import com.canjura.finalproject.dto.checkout.CheckoutDTO;
import com.canjura.finalproject.dto.checkout.CheckoutProductDTO;
import com.canjura.finalproject.entity.checkout.Checkout;
import com.canjura.finalproject.entity.checkout.CheckoutProduct;
import org.springframework.stereotype.Component;
import javax.annotation.processing.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated(value = "org.mapstruct.ap.MappingProcessor")
@Component
public class CheckoutMapperImpl implements CheckoutStructMapper {
    @Override
    public CheckoutDTO checkoutToCheckoutDTO(Checkout checkout){
        if(checkout == null){
            return null;
        }

        CheckoutDTO checkoutDTO = new CheckoutDTO();

        checkoutDTO.setEmail(checkout.getUserEmail());
        checkoutDTO.setAddressInfo(checkout.getAddressInfo());
        checkoutDTO.setPaymentInfo(checkout.getPaymentInfo());
        checkoutDTO.setProducts(checkoutProductsListToCheckoutProductDTOList(checkout.getProducts()));
        return checkoutDTO;
    }

    @Override
    public Checkout checkoutDTOToCheckout(CheckoutDTO checkoutDTO) {
        if(checkoutDTO == null){
            return null;
        }

        Checkout checkout = new Checkout();

        checkout.setUserEmail(checkoutDTO.getEmail());
        checkout.setAddressInfo(checkoutDTO.getAddressInfo());
        checkout.setPaymentInfo(checkoutDTO.getPaymentInfo());
        checkout.setProducts(checkoutProductDTOListToCheckoutProductList(checkoutDTO.getProducts()));
        return checkout;
    }

    @Override
    public CheckoutProductDTO checkoutProductToCheckoutProductDTO(CheckoutProduct checkoutProduct) {
        if(checkoutProduct == null){
            return null;
        }

        CheckoutProductDTO checkoutProductDTO = new CheckoutProductDTO();

        checkoutProductDTO.setName(checkoutProduct.getName());
        checkoutProductDTO.setQuantity(checkoutProduct.getQuantity());
        return checkoutProductDTO;
    }

    @Override
    public CheckoutProduct checkoutProductDTOtoCheckoutProduct(CheckoutProductDTO checkoutProductDTO){
        if(checkoutProductDTO == null){
            return null;
        }

        CheckoutProduct checkoutProduct = new CheckoutProduct();

        checkoutProduct.setName(checkoutProductDTO.getName());
        checkoutProduct.setQuantity(checkoutProductDTO.getQuantity());
        return checkoutProduct;
    }

    private List<CheckoutProductDTO> checkoutProductsListToCheckoutProductDTOList(List<CheckoutProduct> checkoutProducts){
        if(checkoutProducts.isEmpty()){
            return null;
        }

        List<CheckoutProductDTO> list = new ArrayList<>();
        for(CheckoutProduct checkoutProduct : checkoutProducts){
            list.add(checkoutProductToCheckoutProductDTO(checkoutProduct));
        }
        return list;
    }

    private List<CheckoutProduct> checkoutProductDTOListToCheckoutProductList(List<CheckoutProductDTO> checkoutProductDTOList){
        if(checkoutProductDTOList.isEmpty()){
            return null;
        }

        List<CheckoutProduct> list = new ArrayList<>();
        for(CheckoutProductDTO checkoutProductDTO : checkoutProductDTOList){
            list.add(checkoutProductDTOtoCheckoutProduct(checkoutProductDTO));
        }
        return list;
    }
}

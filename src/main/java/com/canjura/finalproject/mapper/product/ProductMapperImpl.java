package com.canjura.finalproject.mapper.product;

import com.canjura.finalproject.dto.product.ProductDTO;
import com.canjura.finalproject.entity.product.Product;
import org.springframework.stereotype.Component;
import javax.annotation.processing.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated(value = "org.mapstruct.ap.MappingProcessor")
@Component
public class ProductMapperImpl implements ProductStructMapper{
    @Override
    public List<ProductDTO> productToProductDTO(List<Product> products) {
        if(products.isEmpty()){
            return null;
        }

        List<ProductDTO> productDTOS = new ArrayList<>();

        for(Product product : products){
            ProductDTO productDTO = new ProductDTO();
            productDTO.setName(product.getName());
            productDTO.setStock(product.getStock());
            productDTO.setPrice(product.getPrice());
            productDTOS.add(productDTO);
        }
        return productDTOS;
    }
}

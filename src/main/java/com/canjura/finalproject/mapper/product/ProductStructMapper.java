package com.canjura.finalproject.mapper.product;

import com.canjura.finalproject.dto.product.ProductDTO;
import com.canjura.finalproject.entity.product.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductStructMapper {
    List<ProductDTO> productToProductDTO(List<Product> products);
}

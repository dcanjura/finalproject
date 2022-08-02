package com.canjura.finalproject.mapper.product;

import com.canjura.finalproject.dto.product.ProductDTO;
import com.canjura.finalproject.entity.product.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class ProductMapperTest {

    private final ProductMapperImpl mapper = new ProductMapperImpl();

    private static final Product product = new Product();

    private static final ProductDTO productDTO = new ProductDTO();

    private static List<Product> products = new ArrayList<>();

    @BeforeEach
    public void createConfig(){
        product.setId(1);
        product.setName("TV");
        product.setStock(20);
        product.setPrice(400.0);
        productDTO.setName("TV");
        productDTO.setStock(20);
        productDTO.setPrice(400.0);
        products.add(product);
    }

    @Test
    @DisplayName("Mapper products to productDTOs")
    void productsToProductDTOsTest(){
        //Arr
        List<ProductDTO> list;

        //Act
        list = this.mapper.productToProductDTO(products);

        //Assert
        Assertions.assertThat(list).isNotNull();
    }

    @Test
    @DisplayName("Mapper null products to productDTOs")
    void nullProductsToProductDTOsTest(){
        //Arr
        List<ProductDTO> list;
        products = new ArrayList<>();

        //Act
        list = this.mapper.productToProductDTO(products);

        //Assert
        Assertions.assertThat(list).isNull();
    }
}

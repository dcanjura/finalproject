package com.canjura.finalproject.controller.product;

import com.canjura.finalproject.dto.product.ProductDTO;
import com.canjura.finalproject.entity.product.Product;
import com.canjura.finalproject.mapper.product.ProductStructMapper;
import com.canjura.finalproject.service.product.ProductService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

class ProductControllerTest {

    @InjectMocks
    private ProductController controller = new ProductController();

    @Mock
    private ProductStructMapper mapper;

    @Mock
    private ProductService service;

    private static final List<Product> products = new ArrayList<>();

    private static final ProductDTO productDTO = new ProductDTO();

    @BeforeEach
     void createProduct(){
        Product product = new Product();
        product.setId(1);
        product.setName("TV");
        product.setStock(10);
        product.setPrice(20.0);
        products.add(product);
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(product.getName());
        productDTO.setStock(product.getStock());
        productDTO.setPrice(product.getPrice());
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Get all products")
    void getAllProductsTest(){
        //Arr
        List<ProductDTO> productDTOList = List.of(productDTO);

        //Act
        Mockito.when(this.service.getProducts()).thenReturn(products);
        Mockito.when(this.mapper.productToProductDTO(products)).thenReturn(productDTOList);

        //Assert
        Assertions.assertThat(this.controller.getProducts()).isEqualTo(ResponseEntity.status(HttpStatus.FOUND).body(mapper.productToProductDTO(products)));
    }

    @Test
    @DisplayName("Get all products fail")
    void getAllProductsFailTest() throws ResponseStatusException{
        //Arr
        ResponseStatusException exception = new ResponseStatusException(HttpStatus.NOT_FOUND, "No products were found");

        //Act
        Mockito.when(this.service.getProducts()).thenReturn(products);
        Mockito.when(this.controller.getProducts()).thenThrow(exception);

        //Assert
        org.junit.jupiter.api.Assertions.assertThrows(ResponseStatusException.class, () -> this.controller.getProducts());
    }
}

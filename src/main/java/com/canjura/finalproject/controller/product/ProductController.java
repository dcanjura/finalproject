package com.canjura.finalproject.controller.product;

import com.canjura.finalproject.dto.product.ProductDTO;
import com.canjura.finalproject.mapper.product.ProductStructMapper;
import com.canjura.finalproject.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("products/")
public class ProductController {
    @Autowired
    private ProductService service;

    @Autowired
    private ProductStructMapper productMapper;

    @GetMapping("get/all")
    public ResponseEntity<List<ProductDTO>> getProducts(){
        if(!service.getProducts().isEmpty()){
            List<ProductDTO> products = productMapper.productToProductDTO(service.getProducts());
            return ResponseEntity.status(HttpStatus.FOUND).body(products);
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No products were found");
        }
    }
}

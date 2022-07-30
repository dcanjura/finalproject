package com.canjura.finalproject.service.product;

import com.canjura.finalproject.entity.product.Product;
import com.canjura.finalproject.repository.product.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepo productRepo;

    public List<Product> getProducts(){
        return productRepo.findAll();
    }
}

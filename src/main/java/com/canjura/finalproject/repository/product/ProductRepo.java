package com.canjura.finalproject.repository.product;

import com.canjura.finalproject.entity.product.Product;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {

    @Bean
    Product findProductByName(String type);
}

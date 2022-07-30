package com.canjura.finalproject.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class ProductDTO {

    @NotBlank(message = "Please enter the product name")
    @NotNull(message = "Product name should not be null")
    @JsonProperty("name")
    private String name;

    @NotNull(message = "Stock amount should not be null")
    @JsonProperty("stock")
    private int stock;

    @NotNull(message = "Please enter the product price")
    @JsonProperty("price")
    private Double price;
}

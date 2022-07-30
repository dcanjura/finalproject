package com.canjura.finalproject.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class OrderProductDTO {
    @NotBlank(message = "Please enter the product name")
    @JsonProperty("name")
    private String name;

    @NotNull(message = "Please enter the product quantity")
    @JsonProperty("quantity")
    private int quantity;
}

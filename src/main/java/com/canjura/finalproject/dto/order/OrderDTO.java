package com.canjura.finalproject.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter @Setter
public class OrderDTO {
    @NotNull(message = "User Info should not be null")
    @JsonProperty("userInfo")
    private String userInfo;

    @NotNull(message = "The email should not be null")
    @NotBlank(message = "Please enter the user email")
    @Email(message = "Email should be a valid one")
    @JsonProperty("userEmail")
    private String useEmail;

    @NotNull(message = "Payment Info should not be null")
    @NotBlank(message = "Please enter the payment info")
    @JsonProperty("paymentInfo")
    private String paymentInfo;

    @NotNull(message = "Products List should not be null")
    @Size(min = 1, message = "It should be at least 1 product")
    @Valid
    private List<OrderProductDTO> products;

    @NotNull(message = "Total amount should not be null")
    @JsonProperty("total")
    private Double total;

    @NotNull(message = "Delivery Info should not be null")
    @NotBlank(message = "Please enter the delivery info")
    @JsonProperty("deliveryInfo")
    private String deliveryInfo;
}

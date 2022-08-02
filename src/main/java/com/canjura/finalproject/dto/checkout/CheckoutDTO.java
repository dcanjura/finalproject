package com.canjura.finalproject.dto.checkout;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@Getter @Setter
public class CheckoutDTO {

    @NotBlank(message = "Please enter the user email")
    @NotNull(message = "Email should not be null")
    @Email(message = "Email should be a valid one")
    @JsonProperty("userEmail")
    private String email;

    @NotBlank(message = "Please enter the address type")
    @NotNull(message = "Address type should not be null")
    @JsonProperty("addressInfo")
    private String addressInfo;

    @NotBlank(message = "Please enter the payment type")
    @NotNull(message = "Payment type should not be null")
    @JsonProperty("paymentInfo")
    private String paymentInfo;


    @NotNull(message = "It should be at least 1 product")
    @Size(min = 1, message = "It should be at least 1 product")
    @Valid
    @JsonProperty("products")
    List<CheckoutProductDTO> products;
}

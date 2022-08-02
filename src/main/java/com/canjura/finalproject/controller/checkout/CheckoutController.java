package com.canjura.finalproject.controller.checkout;

import com.canjura.finalproject.dto.checkout.CheckoutDTO;
import com.canjura.finalproject.mapper.checkout.CheckoutStructMapper;
import com.canjura.finalproject.service.checkout.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.Valid;

@Controller
@RequestMapping("/checkout/")
public class CheckoutController {

    @Autowired
    private CheckoutService service;

    @Autowired
    private CheckoutStructMapper checkoutMapper;


    @GetMapping("get/checkout/by")
    public ResponseEntity<CheckoutDTO> getCheckout(@RequestParam String email){
        if(service.getCheckoutInfo(email) != null){
            return ResponseEntity.status(HttpStatus.FOUND).body(checkoutMapper.checkoutToCheckoutDTO(service.getCheckoutInfo(email)));
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No checkout process found with the given user email");
        }
    }

    @PostMapping("save/checkout")
    public ResponseEntity<String> saveCheckout(@Valid @RequestBody CheckoutDTO checkoutDTO){
        return service.saveCheckoutProcess(checkoutMapper.checkoutDTOToCheckout(checkoutDTO));
    }


    @DeleteMapping("delete/checkout/by")
    public ResponseEntity<String> deleteCheckout(@RequestParam String email) {
        return service.deleteCheckoutByUserEmail(email);
    }
}

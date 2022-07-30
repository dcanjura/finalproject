package com.canjura.finalproject.service.checkout;

import com.canjura.finalproject.entity.checkout.Checkout;
import com.canjura.finalproject.repository.checkout.CheckoutRepo;
import com.canjura.finalproject.repository.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CheckoutService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CheckoutRepo checkoutRepo;

    public Checkout getCheckoutInfo(String email){
        return checkoutRepo.findCheckoutByUserEmail(email);
    }

    public ResponseEntity<String> saveCheckoutProcess(Checkout checkout){
        if(checkoutRepo.findCheckoutByUserEmail(checkout.getUserEmail()) == null){
            if(userRepo.findUserByEmail(checkout.getUserEmail()) != null){
                checkoutRepo.save(checkout);
                return ResponseEntity.status(HttpStatus.CREATED).body("Checkout process saved temporarily");
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("We could not find a user with the given email");
            }
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Checkout process is already on file");
        }
    }

    public ResponseEntity<String> updateCheckoutProcess(Checkout checkout){
        if(checkout.getProducts().isEmpty()){
            checkoutRepo.deleteAll();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Products went to 0, checkout process was deleted");
        }else {
            Checkout tempCheckout = checkoutRepo.findCheckoutByUserEmail(checkout.getUserEmail());
            checkout.setId(tempCheckout.getId());
            checkoutRepo.save(checkout);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Checkout process with user email: "+ checkout.getUserEmail() + " updated");
        }
    }



    public ResponseEntity<String> deleteCheckoutByUserEmail(String email){
        if(checkoutRepo.findCheckoutByUserEmail(email) != null){
            checkoutRepo.deleteCheckoutByUserEmail(email);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Checkout process deleted successfully");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Checkout process not found with the given user email");
        }
    }
}

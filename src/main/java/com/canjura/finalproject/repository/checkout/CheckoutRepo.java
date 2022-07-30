package com.canjura.finalproject.repository.checkout;

import com.canjura.finalproject.entity.checkout.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface CheckoutRepo extends JpaRepository<Checkout, Integer> {
    Checkout findCheckoutByUserEmail(String email);

    @Transactional
    void deleteCheckoutByUserEmail(String email);
}

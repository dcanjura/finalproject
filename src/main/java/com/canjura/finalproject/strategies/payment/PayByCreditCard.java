package com.canjura.finalproject.strategies.payment;

import com.canjura.finalproject.entity.User;
import com.canjura.finalproject.entity.UserPayment;

import java.util.List;

public class PayByCreditCard implements PayStrategy{

    @Override
    public UserPayment getPayment(User user, String type) {
        List<UserPayment> payments = user.getPayments();
        for(UserPayment payment : payments){
            if(payment.getType().equalsIgnoreCase(type)){
                return payment;
            }
        }
        return null;
    }
}

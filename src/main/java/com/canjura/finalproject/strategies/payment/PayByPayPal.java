package com.canjura.finalproject.strategies.payment;

import com.canjura.finalproject.entity.user.User;
import com.canjura.finalproject.entity.user.UserPayment;
import java.util.List;

public class PayByPayPal implements PayStrategy{

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

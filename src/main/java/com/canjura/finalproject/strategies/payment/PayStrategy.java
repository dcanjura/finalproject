package com.canjura.finalproject.strategies.payment;

import com.canjura.finalproject.entity.User;
import com.canjura.finalproject.entity.UserPayment;

public interface PayStrategy {
    UserPayment getPayment(User user, String type);
}

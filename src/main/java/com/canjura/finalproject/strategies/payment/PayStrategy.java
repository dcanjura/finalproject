package com.canjura.finalproject.strategies.payment;

import com.canjura.finalproject.entity.user.User;
import com.canjura.finalproject.entity.user.UserPayment;

public interface PayStrategy {
    UserPayment getPayment(User user, String type);
}

package com.canjura.finalproject.strategies.address;

import com.canjura.finalproject.entity.user.User;
import com.canjura.finalproject.entity.user.UserAddress;

public interface AddressStrategy {
    UserAddress getAddress(User user, String type);
}

package com.canjura.finalproject.strategies.address;

import com.canjura.finalproject.entity.User;
import com.canjura.finalproject.entity.UserAddress;

public interface AddressStrategy {
    UserAddress getAddress(User user, String type);
}

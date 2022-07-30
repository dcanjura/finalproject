package com.canjura.finalproject.strategies.address;

import com.canjura.finalproject.entity.user.User;
import com.canjura.finalproject.entity.user.UserAddress;
import java.util.List;

public class WorkAddress implements AddressStrategy{

    @Override
    public UserAddress getAddress(User user, String type) {
        List<UserAddress> addresses = user.getAddress();
        for(UserAddress address : addresses){
            if(address.getType().equalsIgnoreCase(type)){
                return address;
            }
        }
        return null;
    }
}

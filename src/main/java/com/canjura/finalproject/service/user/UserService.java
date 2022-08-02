package com.canjura.finalproject.service.user;

import com.canjura.finalproject.entity.User;
import com.canjura.finalproject.entity.UserAddress;
import com.canjura.finalproject.entity.UserPayment;
import com.canjura.finalproject.repository.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepo repo;

    public ResponseEntity<String> getUserInfo(String email){
        if(repo.findUserByEmail(email) != null){
            User user = repo.findUserByEmail(email);
            List<String> addresses = getAddresses(user.getAddress());
            List<String> payments = getPayments(user.getPayments());
            return ResponseEntity.status(HttpStatus.FOUND).body("User info:" + user.getName() + " " + user.getPhone() + " addresses info: " + addresses + " payments info: " + payments);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("We could not find a user with the given email");
        }
    }

    public List<String> getAddresses(List<UserAddress> addresses){
        List<String> list = new ArrayList<>();
        for(UserAddress address : addresses){
            list.add(address.getType() + ": " + address.getAddress());
        }
        return list;
    }

    public List<String> getPayments(List<UserPayment> payments){
        List<String> list = new ArrayList<>();
        for(UserPayment payment : payments){
            list.add(payment.getType() + ": " + payment.getPayment());
        }
        return list;
    }
}

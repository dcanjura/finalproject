package com.canjura.finalproject.service.user;

import com.canjura.finalproject.entity.user.User;
import com.canjura.finalproject.entity.user.UserAddress;
import com.canjura.finalproject.repository.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    private User tempUser;

    public ResponseEntity<List<User>> getUsers(){
        if(userRepo.findAll().isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(userRepo.findAll());
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(userRepo.findAll());
        }
    }

    public ResponseEntity<String> getUserByEmail(String email){
        if(userRepo.findUserByEmail(email) != null){
            tempUser = userRepo.findUserByEmail(email);
            List<String> address = getAddresses(tempUser.getAddress());
            return ResponseEntity.status(HttpStatus.FOUND).body("User: " + tempUser.getName() + " " + tempUser.getPhone() + " " + tempUser.getEmail() + ", addresses: " + Arrays.toString(address.toArray()));
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("We could not find a user with given email");
        }
    }

    private List<String> getAddresses(List<UserAddress> addressList){
        List<String> addresses = new ArrayList<>();
        for(UserAddress address : addressList){
            addresses.add(address.getAddress());
        }
        return addresses;
    }

    public ResponseEntity<String> saveUser(User user){
        tempUser = userRepo.findUserByEmail(user.getEmail());
        if(tempUser != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The user already exists with given email");
        }else {
            tempUser = userRepo.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("User with email: "+ tempUser.getEmail() +" registered");
        }
    }

    public ResponseEntity<String> updateUser(User user){
        tempUser = userRepo.findUserByEmail(user.getEmail());
        if(tempUser != null){
            user.setId(tempUser.getId());
            userRepo.save(user);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("User with email " + user.getEmail() + " updated");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("We could not find a user with given email");
        }
    }

    public ResponseEntity<String> deleteUsers(){
        if(userRepo.findAll().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No records found to delete");
        }else {
            userRepo.deleteAll();
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("All records were deleted");
        }
    }

    public ResponseEntity<String> deleteUserByEmail(String email){
        if(userRepo.findUserByEmail(email) != null){
            userRepo.deleteUserByEmail(email);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("User with email: " + email + " was deleted successfully");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("We could not find a user with given email");
        }
    }
}

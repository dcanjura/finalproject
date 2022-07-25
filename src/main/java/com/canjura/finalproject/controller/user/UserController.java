package com.canjura.finalproject.controller.user;

import com.canjura.finalproject.entity.user.User;
import com.canjura.finalproject.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users/")
public class UserController {

    @Autowired
    UserService service;

    @GetMapping("get/all")
    public ResponseEntity<List<User>> getUsers(){
        return service.getUsers();
    }

    @GetMapping("/get/user")
    public ResponseEntity<String> getUserByEmail(@RequestParam String email){
        return service.getUserByEmail(email);
    }

    @PostMapping("/add/user")
    public ResponseEntity<String> saveUser(@Valid @RequestBody User user){
        return service.saveUser(user);
    }

    @PutMapping("/update/user")
    public ResponseEntity<String> updateUser(@RequestBody User user){
        return service.updateUser(user);
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<String> deleteAllUsers(){
        return service.deleteUsers();
    }

    @DeleteMapping("/delete/user")
    public ResponseEntity<String> deleteUser(@RequestParam String email){
        return service.deleteUserByEmail(email);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}

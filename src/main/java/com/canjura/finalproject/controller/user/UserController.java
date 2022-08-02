package com.canjura.finalproject.controller.user;

import com.canjura.finalproject.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user/")
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping("get/by")
    public ResponseEntity<String> getUserBy(@RequestParam String email){
        return service.getUserInfo(email);
    }
}

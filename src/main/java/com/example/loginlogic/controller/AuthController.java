package com.example.loginlogic.controller;

import com.example.loginlogic.dto.AuthRequest;
import com.example.loginlogic.jwt.JwtUtil;
import com.example.loginlogic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AuthController {
    @Autowired
    private UserService userService;


    @PostMapping("/join")
    public ResponseEntity<?> joinUser(@RequestBody AuthRequest authRequest) {
        return userService.joinUser(authRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AuthRequest authRequest) {
        return userService.loginUser(authRequest);
    }
}

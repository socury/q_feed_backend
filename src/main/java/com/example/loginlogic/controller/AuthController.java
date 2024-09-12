package com.example.loginlogic.controller;

import com.example.loginlogic.dto.ErrorResponse;
import com.example.loginlogic.dto.login.LoginRequest;
import com.example.loginlogic.jwt.JwtUtil;
import com.example.loginlogic.service.UserService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("auth")
public class AuthController {
    private final static Logger logger = Logger.getLogger(AuthController.class.getName());

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> joinUser(@RequestBody LoginRequest authRequest) {
        return userService.joinUser(authRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest authRequest) {
        return userService.loginUser(authRequest);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshUser(
        @RequestHeader("Authorization") String refreshToken
    ) {
        String token = refreshToken.substring(7);

        if(jwtUtil.isTokenValid(token)) {
            return userService.refreshToken(token);
        }else{
            return ResponseEntity.status(401).body(new ErrorResponse("토큰을 찾을수 없습니다.", "토큰 유효성 오류"));
        }
    }
}

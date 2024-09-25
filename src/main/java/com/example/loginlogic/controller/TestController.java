package com.example.loginlogic.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
public class TestController {
    
    @PostMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/api")
    public ResponseEntity<Object> authHeaderChecker(HttpServletRequest request) {
        Map<String, String> response = new HashMap<>(){{
            put("Authorization", request.getHeader("Authorization"));
        }};
        return ResponseEntity.ok(response);
    }
}

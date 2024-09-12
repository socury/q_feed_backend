package com.example.loginlogic.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("main")
public class TestController {
    @PostMapping("/test")
    public String test() {
        return "test";
    }
}

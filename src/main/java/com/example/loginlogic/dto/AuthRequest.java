package com.example.loginlogic.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record AuthRequest(

        @NotNull
        String username,

        @NotNull
        String password,

        @Email
        String email
) {

}

package com.example.loginlogic.dto.register;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record RegisterRequest(

        @NotNull
        String username,

        @NotNull
        String password,

        @Email
        String email
) {

}

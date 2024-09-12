package com.example.loginlogic.dto.login;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class LoginResponse {
    private String access_token;
    private String refresh_token;
    private String token_type;
}

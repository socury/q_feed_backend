package com.example.loginlogic.dto.login;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private String tokenType;
}

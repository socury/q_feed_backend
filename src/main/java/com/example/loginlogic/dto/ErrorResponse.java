package com.example.loginlogic.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
public class ErrorResponse {

    private String message;

    public String error;

}

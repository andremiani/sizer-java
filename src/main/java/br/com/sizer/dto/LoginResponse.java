package br.com.sizer.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class LoginResponse {
    private String token;

    private String expiresIn;

    // Getters and setters...
}
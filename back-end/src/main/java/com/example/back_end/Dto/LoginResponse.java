package com.example.back_end.Dto;

public class LoginResponse {
    public String token;
    public String role;
    public String email;

    public LoginResponse(String token, String role, String email) {
        this.token = token;
        this.role = role;
        this.email = email;
    }
}

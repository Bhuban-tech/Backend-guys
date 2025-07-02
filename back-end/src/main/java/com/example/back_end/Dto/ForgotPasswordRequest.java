package com.example.back_end.Dto;

public class ForgotPasswordRequest {
    private String email;

    // No-arg constructor
    public ForgotPasswordRequest() {}

    // All-args constructor (optional)
    public ForgotPasswordRequest(String email) {
        this.email = email;
    }

    // Getter
    public String getEmail() {
        return email;
    }

    // Setter
    public void setEmail(String email) {
        this.email = email;
    }
}

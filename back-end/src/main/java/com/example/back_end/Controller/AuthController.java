package com.example.back_end.Controller;

import com.example.back_end.Dto.ForgotPasswordRequest;
import com.example.back_end.Dto.LoginRequest;
import com.example.back_end.Dto.ResetPasswordRequest;
import com.example.back_end.Entity.JwtUtil;
import com.example.back_end.Entity.User;
import com.example.back_end.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        Optional<User> userOpt = repo.findByEmail(req.email);
        if (userOpt.isEmpty() || !encoder.matches(req.password, userOpt.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials.");
        }

        User user = userOpt.get();
        String token = jwtUtil.generateToken(user.getEmail());

        return ResponseEntity.ok(Map.of(
                "message", "Login successful",
                "email", user.getEmail(),
                "role", user.getRole(),
                "token", token
        ));
    }

    @PostMapping("/forgot")
    public ResponseEntity<?> forgot(@RequestBody ForgotPasswordRequest req) {
        Optional<User> userOpt = repo.findByEmail(req.getEmail());
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Email not found.");
        }

        String otp = String.format("%06d", new Random().nextInt(999999));
        User user = userOpt.get();
        user.setOtp(otp);
        repo.save(user);

        System.out.println("OTP for " + user.getEmail() + ": " + otp); // Simulate email

        return ResponseEntity.ok("OTP sent to email.");
    }

    @PostMapping("/reset")
    public ResponseEntity<?> reset(@RequestBody ResetPasswordRequest req) {
        Optional<User> userOpt = repo.findByEmail(req.getEmail());
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Email not found.");
        }

        User user = userOpt.get();
        if (!req.getOtp().equals(user.getOtp())) {
            return ResponseEntity.badRequest().body("Invalid OTP.");
        }

        user.setPassword(encoder.encode(req.getNewPassword()));
        user.setOtp(null);
        repo.save(user);

        return ResponseEntity.ok("Password reset successful.");
    }
}

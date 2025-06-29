package com.example.back_end.Dto;

import example.demo.Dto.ForgotPasswordRequest;
import example.demo.Dto.LoginRequest;
import example.demo.Dto.ResetPasswordRequest;
import example.demo.Dto.SignupRequest;
import example.demo.Entity.EmailService;
import example.demo.Entity.JwtUtil;
import example.demo.Entity.User;
import example.demo.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtUtil jwtUtil;

    // Signup Endpoint
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest req) {
        if (repo.findByEmail(req.email).isPresent()) {
            return ResponseEntity.badRequest().body("Email already registered.");
        }

        User user = new User();
        user.setFirstName(req.firstName);
        user.setLastName(req.lastName);
        user.setEmail(req.email);
        user.setPhone(req.phone);
        user.setRole((req.role != null && !req.role.trim().isEmpty()) ? req.role.toUpperCase() : "USER");
        user.setPassword(encoder.encode(req.password));

        repo.save(user);
        return ResponseEntity.ok("Signup successful.");
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        Optional<User> userOpt = repo.findByEmail(req.email);
        if (userOpt.isEmpty() || !encoder.matches(req.password, userOpt.get().getPassword())) {
            return ResponseEntity.status(401).body("Invalid email or password.");
        }

        User user = userOpt.get();
        String token = jwtUtil.generateToken(user.getEmail());

        return ResponseEntity.ok(Map.of(
                "message", "Login successful",
                "token", token,
                "role", user.getRole(),
                "email", user.getEmail()
        ));
    }


    @PostMapping("/forgot")
    public ResponseEntity<?> forgot(@RequestBody ForgotPasswordRequest req) {
        String email = req.getEmail();
        Optional<User> userOpt = repo.findByEmail(email);

        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Email not found.");
        }

        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
        User user = userOpt.get();
        user.setOtp(otp);
        repo.save(user);

        emailService.sendOtp(user.getEmail(), otp);
        return ResponseEntity.ok("OTP sent to email.");
    }

    @PostMapping("/reset")
    public ResponseEntity<?> reset(@RequestBody ResetPasswordRequest req) {
        String email = req.getEmail();
        String otp = req.getOtp();
        String newPassword = req.getNewPassword();

        Optional<User> userOpt = repo.findByEmail(email);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Email not found.");
        }

        User user = userOpt.get();
        if (!otp.equals(user.getOtp())) {
            return ResponseEntity.badRequest().body("Invalid OTP.");
        }

        user.setPassword(encoder.encode(newPassword));
        user.setOtp(null);
        repo.save(user);

        return ResponseEntity.ok("Password reset successful.");
    }

}

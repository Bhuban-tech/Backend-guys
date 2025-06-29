package com.example.back_end.Service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Dummy user (replace with database logic)
        if (!"testuser".equals(username)) {
            throw new UsernameNotFoundException("User not found");
        }

        return User
                .builder()
                .username("testuser")
                .password("$2a$10$7XsImrB1QqZ7xCZ5RyFgVu8HVoN8vVg6PaKOp8zUu9aW0nW9pbqzi") // password: test123
                .authorities(Collections.emptyList())
                .build();
    }
}

package com.example.back_end.Config;

import example.demo.Entity.JwtUtil;
import example.demo.Security.JwtAuthFilter;
import example.demo.Service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userService;
    private final JwtAuthFilter jwtAuthFilter;
    private final JwtUtil jwtUtil;

    // ✅ Redirect URL after successful OAuth2 login — clean YouTube link
    private static final String YOUTUBE_REDIRECT_URL = "https://www.youtube.com/watch?v=bm0OyhwFDuY";

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/auth/**",
                                "/oauth2/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/code/google") // Custom login endpoint (optional)
                        .successHandler((request, response, authentication) -> {
                            try {
                                // Optional: Generate token (for logging, database, etc.)
                                String token = jwtUtil.generateToken(authentication.getName());

                                System.out.println("OAuth2 login successful for user: " + authentication.getName());
                                System.out.println("Generated JWT (not used in redirect): " + token);

                                // ✅ Clean redirect to YouTube — no token in URL
                                response.sendRedirect(YOUTUBE_REDIRECT_URL);
                            } catch (Exception e) {
                                e.printStackTrace();
                                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "OAuth2 login redirect failed.");
                            }
                        })
                        .failureHandler((request, response, exception) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json");
                            response.getWriter().write("{\"error\": \"OAuth2 Authentication Failed: " + exception.getMessage() + "\"}");
                        })
                )
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

package com.example.taskmanager.controller;

import com.example.taskmanager.entity.User;
import com.example.taskmanager.security.JwtUtil;
import com.example.taskmanager.service.UserDetailsServiceImpl;
import com.example.taskmanager.service.UserService;
import lombok.*;


import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        User user = userService.registerUser(req.getUsername(), req.getPassword(), "ROLE_USER");
        return ResponseEntity.ok("Usuario registrado: " + user.getUsername());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest req) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(req.getUsername());
        Set<String> roles = userDetails.getAuthorities().stream()
                .map(auth -> auth.getAuthority())
                .collect(Collectors.toSet());

        String jwt = jwtUtil.generateToken(userDetails.getUsername(), roles);

        return ResponseEntity.ok(new AuthResponse(jwt));
    }
}

@Data
class RegisterRequest {
    private String username;
    private String password;
}

@Data
class AuthRequest {
    private String username;
    private String password;
}

@Data
@AllArgsConstructor
class AuthResponse {
    private String token;
}

package com.uberclone.controller;

import com.uberclone.dto.AuthResponse;
import com.uberclone.dto.LoginRequest;
import com.uberclone.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.uberclone.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody RegisterRequest request) {

        try {

            authService.register(request);

            return ResponseEntity.ok(
                    "User Registered Successfully"
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(409)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest request) {

        String token = authService.login(request);

        return ResponseEntity.ok(
                new AuthResponse(token)
        );
    }
}
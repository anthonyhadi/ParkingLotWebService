package com.anthony.parkinglot.controller;

import com.anthony.parkinglot.dto.AuthRequest;
import com.anthony.parkinglot.dto.AuthResponse;
import com.anthony.parkinglot.dto.RegisterRequest;
import com.anthony.parkinglot.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication management APIs")
public class AuthController {
    
    private final AuthService authService;
    
    @Operation(
            summary = "Register a new user",
            description = "Create a new user account with username, password, and email"
    )
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
    
    @Operation(
            summary = "Login user",
            description = "Authenticate user with username and password"
    )
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
} 
package com.anthony.parkinglot.dto;

import jakarta.validation.constraints.NotBlank;

public record UserIdRequest(
    @NotBlank(message = "Username is required")
    String username,
    
    @NotBlank(message = "Password is required")
    String password
) {} 
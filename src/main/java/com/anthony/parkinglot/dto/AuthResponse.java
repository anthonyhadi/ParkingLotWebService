package com.anthony.parkinglot.dto;

public record AuthResponse(
    String token,
    String username,
    String role
) {} 
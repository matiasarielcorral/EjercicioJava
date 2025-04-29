package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.UUID;

// UserResponse.java
public class UserResponse {
    private UUID id;
    private LocalDateTime created;
    private LocalDateTime modified;
    private LocalDateTime lastLogin;
    private String token;
    private boolean isActive;

    // Getters y Setters
}

package com.cadence.music.dto;

import java.util.Set;

public class AuthResponse {
    private String token;
    private String tokenType = "Bearer";
    private Long userId;
    private String email;
    private String displayName;
    private Set<String> roles;

    public AuthResponse(String token, Long userId, String email, String displayName, Set<String> roles) {
        this.token = token;
        this.userId = userId;
        this.email = email;
        this.displayName = displayName;
        this.roles = roles;
    }

    public String getToken() { return token; }
    public String getTokenType() { return tokenType; }
    public Long getUserId() { return userId; }
    public String getEmail() { return email; }
    public String getDisplayName() { return displayName; }
    public Set<String> getRoles() { return roles; }
}
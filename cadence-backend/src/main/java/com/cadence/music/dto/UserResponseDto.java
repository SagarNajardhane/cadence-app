package com.cadence.music.dto;

import java.util.Set;

public class UserResponseDto {
    private Long id;
    private String email;
    private String displayName;
    private String avatarUrl;
    private Set<String> roles;

    public UserResponseDto(Long id, String email, String displayName, String avatarUrl, Set<String> roles) {
        this.id = id;
        this.email = email;
        this.displayName = displayName;
        this.avatarUrl = avatarUrl;
        this.roles = roles;
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getDisplayName() { return displayName; }
    public String getAvatarUrl() { return avatarUrl; }
    public Set<String> getRoles() { return roles; }
}
package com.cadence.music.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PlaylistRequestDto {

    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;

    private String description;
    private String coverArtUrl;
    private Boolean isPublic = true;

    public PlaylistRequestDto() {}

    public PlaylistRequestDto(String title, String description, String coverArtUrl, Boolean isPublic) {
        this.title = title;
        this.description = description;
        this.coverArtUrl = coverArtUrl;
        this.isPublic = isPublic;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCoverArtUrl() { return coverArtUrl; }
    public void setCoverArtUrl(String coverArtUrl) { this.coverArtUrl = coverArtUrl; }

    public Boolean getIsPublic() { return isPublic; }
    public void setIsPublic(Boolean isPublic) { this.isPublic = isPublic; }
}
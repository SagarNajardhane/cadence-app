package com.cadence.music.dto;

import java.time.ZonedDateTime;
import java.util.List;

public class PlaylistResponseDto {
    private Long id;
    private Long userId;
    private String userDisplayName;
    private String title;
    private String description;
    private String coverArtUrl;
    private Boolean isPublic;
    private ZonedDateTime createdAt;
    private List<TrackResponseDto> tracks;

    public PlaylistResponseDto() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUserDisplayName() { return userDisplayName; }
    public void setUserDisplayName(String userDisplayName) { this.userDisplayName = userDisplayName; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCoverArtUrl() { return coverArtUrl; }
    public void setCoverArtUrl(String coverArtUrl) { this.coverArtUrl = coverArtUrl; }

    public Boolean getIsPublic() { return isPublic; }
    public void setIsPublic(Boolean isPublic) { this.isPublic = isPublic; }

    public ZonedDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(ZonedDateTime createdAt) { this.createdAt = createdAt; }

    public List<TrackResponseDto> getTracks() { return tracks; }
    public void setTracks(List<TrackResponseDto> tracks) { this.tracks = tracks; }
}
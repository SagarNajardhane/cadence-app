package com.cadence.music.dto;

import jakarta.validation.constraints.NotNull;

public class AddTrackToPlaylistRequestDto {

    @NotNull(message = "Track ID is required")
    private Long trackId;

    public AddTrackToPlaylistRequestDto() {}

    public AddTrackToPlaylistRequestDto(Long trackId) {
        this.trackId = trackId;
    }

    public Long getTrackId() { return trackId; }
    public void setTrackId(Long trackId) { this.trackId = trackId; }
}
package com.cadence.music.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TrackCreateRequestDto {

    @NotBlank(message = "Track title is required")
    private String title;

    @NotBlank(message = "Artist name is required")
    private String artistName;

    @NotBlank(message = "Audio URL is required")
    private String audioUrl;

    private String coverArtUrl;

    @NotNull(message = "Duration in seconds is required")
    private Integer durationSeconds;

    public TrackCreateRequestDto() {}

    public TrackCreateRequestDto(String title, String artistName, String audioUrl, String coverArtUrl, Integer durationSeconds) {
        this.title = title;
        this.artistName = artistName;
        this.audioUrl = audioUrl;
        this.coverArtUrl = coverArtUrl;
        this.durationSeconds = durationSeconds;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getArtistName() { return artistName; }
    public void setArtistName(String artistName) { this.artistName = artistName; }

    public String getAudioUrl() { return audioUrl; }
    public void setAudioUrl(String audioUrl) { this.audioUrl = audioUrl; }

    public String getCoverArtUrl() { return coverArtUrl; }
    public void setCoverArtUrl(String coverArtUrl) { this.coverArtUrl = coverArtUrl; }

    public Integer getDurationSeconds() { return durationSeconds; }
    public void setDurationSeconds(Integer durationSeconds) { this.durationSeconds = durationSeconds; }
}
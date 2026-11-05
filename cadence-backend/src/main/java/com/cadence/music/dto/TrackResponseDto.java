package com.cadence.music.dto;

import java.util.Set;

public class TrackResponseDto {
    private Long id;
    private String title;
    private String artistName;
    private Long artistId;
    private String albumTitle;
    private Long albumId;
    private String coverArtUrl;
    private Integer durationSeconds;
    private String audioUrl;
    private Long playCount;
    private Set<String> genres;

    public TrackResponseDto() {}

    public TrackResponseDto(Long id, String title, String artistName, Long artistId, 
                            String albumTitle, Long albumId, String coverArtUrl, 
                            Integer durationSeconds, String audioUrl, Long playCount, Set<String> genres) {
        this.id = id;
        this.title = title;
        this.artistName = artistName;
        this.artistId = artistId;
        this.albumTitle = albumTitle;
        this.albumId = albumId;
        this.coverArtUrl = coverArtUrl;
        this.durationSeconds = durationSeconds;
        this.audioUrl = audioUrl;
        this.playCount = playCount;
        this.genres = genres;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getArtistName() { return artistName; }
    public void setArtistName(String artistName) { this.artistName = artistName; }

    public Long getArtistId() { return artistId; }
    public void setArtistId(Long artistId) { this.artistId = artistId; }

    public String getAlbumTitle() { return albumTitle; }
    public void setAlbumTitle(String albumTitle) { this.albumTitle = albumTitle; }

    public Long getAlbumId() { return albumId; }
    public void setAlbumId(Long albumId) { this.albumId = albumId; }

    public String getCoverArtUrl() { return coverArtUrl; }
    public void setCoverArtUrl(String coverArtUrl) { this.coverArtUrl = coverArtUrl; }

    public Integer getDurationSeconds() { return durationSeconds; }
    public void setDurationSeconds(Integer durationSeconds) { this.durationSeconds = durationSeconds; }

    public String getAudioUrl() { return audioUrl; }
    public void setAudioUrl(String audioUrl) { this.audioUrl = audioUrl; }

    public Long getPlayCount() { return playCount; }
    public void setPlayCount(Long playCount) { this.playCount = playCount; }

    public Set<String> getGenres() { return genres; }
    public void setGenres(Set<String> genres) { this.genres = genres; }
}
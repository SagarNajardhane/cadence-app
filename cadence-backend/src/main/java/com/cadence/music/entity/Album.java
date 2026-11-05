package com.cadence.music.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "albums")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id", nullable = false)
    private Artist artist;

    @Column(nullable = false)
    private String title;

    @Column(name = "cover_art_url")
    private String coverArtUrl;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "album_type")
    private String albumType;

    public Album() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Artist getArtist() { return artist; }
    public void setArtist(Artist artist) { this.artist = artist; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCoverArtUrl() { return coverArtUrl; }
    public void setCoverArtUrl(String coverArtUrl) { this.coverArtUrl = coverArtUrl; }

    public LocalDate getReleaseDate() { return releaseDate; }
    public void setReleaseDate(LocalDate releaseDate) { this.releaseDate = releaseDate; }

    public String getAlbumType() { return albumType; }
    public void setAlbumType(String albumType) { this.albumType = albumType; }
}
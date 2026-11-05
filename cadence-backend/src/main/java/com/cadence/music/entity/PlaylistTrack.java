package com.cadence.music.entity;

import jakarta.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "playlist_tracks")
public class PlaylistTrack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "playlist_id", nullable = false)
    private Playlist playlist;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "track_id", nullable = false)
    private Track track;

    @Column(nullable = false)
    private Integer position;

    @Column(name = "added_at", insertable = false, updatable = false)
    private ZonedDateTime addedAt;

    public PlaylistTrack() {}

    public PlaylistTrack(Playlist playlist, Track track, Integer position) {
        this.playlist = playlist;
        this.track = track;
        this.position = position;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Playlist getPlaylist() { return playlist; }
    public void setPlaylist(Playlist playlist) { this.playlist = playlist; }

    public Track getTrack() { return track; }
    public void setTrack(Track track) { this.track = track; }

    public Integer getPosition() { return position; }
    public void setPosition(Integer position) { this.position = position; }

    public ZonedDateTime getAddedAt() { return addedAt; }
    public void setAddedAt(ZonedDateTime addedAt) { this.addedAt = addedAt; }
}
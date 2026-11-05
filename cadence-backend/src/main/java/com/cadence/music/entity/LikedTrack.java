package com.cadence.music.entity;

import jakarta.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "liked_tracks")
@IdClass(LikedTrackId.class)
public class LikedTrack {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "track_id")
    private Long trackId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "track_id", insertable = false, updatable = false)
    private Track track;

    @Column(name = "liked_at", insertable = false, updatable = false)
    private ZonedDateTime likedAt;

    public LikedTrack() {}

    public LikedTrack(Long userId, Long trackId) {
        this.userId = userId;
        this.trackId = trackId;
    }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getTrackId() { return trackId; }
    public void setTrackId(Long trackId) { this.trackId = trackId; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Track getTrack() { return track; }
    public void setTrack(Track track) { this.track = track; }

    public ZonedDateTime getLikedAt() { return likedAt; }
    public void setLikedAt(ZonedDateTime likedAt) { this.likedAt = likedAt; }
}
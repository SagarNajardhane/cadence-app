package com.cadence.music.entity;

import java.io.Serializable;
import java.util.Objects;

public class LikedTrackId implements Serializable {

    private Long userId;
    private Long trackId;

    public LikedTrackId() {}

    public LikedTrackId(Long userId, Long trackId) {
        this.userId = userId;
        this.trackId = trackId;
    }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getTrackId() { return trackId; }
    public void setTrackId(Long trackId) { this.trackId = trackId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LikedTrackId that = (LikedTrackId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(trackId, that.trackId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, trackId);
    }
}
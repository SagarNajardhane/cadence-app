package com.cadence.music.repository;

import com.cadence.music.entity.PlaylistTrack;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PlaylistTrackRepository extends JpaRepository<PlaylistTrack, Long> {
    Optional<PlaylistTrack> findByPlaylistIdAndTrackId(Long playlistId, Long trackId);
    void deleteByPlaylistIdAndTrackId(Long playlistId, Long trackId);
}
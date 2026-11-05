package com.cadence.music.repository;

import com.cadence.music.entity.LikedTrack;
import com.cadence.music.entity.LikedTrackId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LikedTrackRepository extends JpaRepository<LikedTrack, LikedTrackId> {
    List<LikedTrack> findByUserIdOrderByLikedAtDesc(Long userId);
    boolean existsByUserIdAndTrackId(Long userId, Long trackId);
    void deleteByUserIdAndTrackId(Long userId, Long trackId);
}
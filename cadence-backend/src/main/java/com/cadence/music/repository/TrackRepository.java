package com.cadence.music.repository;

import com.cadence.music.entity.Track;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {
    @Query("SELECT t FROM Track t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(t.artist.name) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<Track> searchTracks(@Param("query") String query, Pageable pageable);
}
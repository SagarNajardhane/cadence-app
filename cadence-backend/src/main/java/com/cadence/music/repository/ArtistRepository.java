package com.cadence.music.repository;

import com.cadence.music.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Optional<Artist> findByNameIgnoreCase(String name);
}
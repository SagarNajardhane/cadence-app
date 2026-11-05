package com.cadence.music.service;

import com.cadence.music.dto.TrackCreateRequestDto;
import com.cadence.music.dto.TrackResponseDto;
import com.cadence.music.entity.Artist;
import com.cadence.music.entity.Genre;
import com.cadence.music.entity.Track;
import com.cadence.music.repository.ArtistRepository;
import com.cadence.music.repository.TrackRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
public class TrackService {

    private final TrackRepository trackRepository;
    private final ArtistRepository artistRepository;

    public TrackService(TrackRepository trackRepository, ArtistRepository artistRepository) {
        this.trackRepository = trackRepository;
        this.artistRepository = artistRepository;
    }

    @Transactional(readOnly = true)
    public Page<TrackResponseDto> getAllTracks(int page, int size) {
        return trackRepository.findAll(PageRequest.of(page, size))
                .map(this::mapToDto);
    }

    @Transactional(readOnly = true)
    public Page<TrackResponseDto> searchTracks(String query, int page, int size) {
        return trackRepository.searchTracks(query, PageRequest.of(page, size))
                .map(this::mapToDto);
    }

    @Transactional(readOnly = true)
    public TrackResponseDto getTrackById(Long id) {
        Track track = trackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Track not found with id: " + id));
        return mapToDto(track);
    }

    @Transactional
    public TrackResponseDto createTrack(TrackCreateRequestDto dto) {
        Artist artist = artistRepository.findAll().stream()
                .filter(a -> a.getName().equalsIgnoreCase(dto.getArtistName()))
                .findFirst()
                .orElseGet(() -> {
                    Artist newArtist = new Artist();
                    newArtist.setName(dto.getArtistName());
                    return artistRepository.save(newArtist);
                });

        Track track = new Track();
        track.setTitle(dto.getTitle());
        track.setArtist(artist);
        track.setAudioUrl(dto.getAudioUrl());
        track.setDurationSeconds(dto.getDurationSeconds() != null ? dto.getDurationSeconds() : 180);
        track.setPlayCount(0L);

        Track saved = trackRepository.save(track);
        return mapToDto(saved);
    }

    @Transactional
    public void incrementPlayCount(Long id) {
        Track track = trackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Track not found with id: " + id));
        track.setPlayCount(track.getPlayCount() + 1);
        trackRepository.save(track);
    }

    private TrackResponseDto mapToDto(Track track) {
        TrackResponseDto dto = new TrackResponseDto();
        dto.setId(track.getId());
        dto.setTitle(track.getTitle());
        dto.setArtistName(track.getArtist().getName());
        dto.setArtistId(track.getArtist().getId());
        dto.setAlbumTitle(track.getAlbum() != null ? track.getAlbum().getTitle() : null);
        dto.setAlbumId(track.getAlbum() != null ? track.getAlbum().getId() : null);
        dto.setCoverArtUrl(track.getAlbum() != null ? track.getAlbum().getCoverArtUrl() : null);
        dto.setDurationSeconds(track.getDurationSeconds());
        dto.setAudioUrl(track.getAudioUrl());
        dto.setPlayCount(track.getPlayCount());
        dto.setGenres(track.getGenres().stream().map(Genre::getName).collect(Collectors.toSet()));
        return dto;
    }
}
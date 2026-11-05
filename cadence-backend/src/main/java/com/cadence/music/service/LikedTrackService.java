package com.cadence.music.service;

import com.cadence.music.dto.TrackResponseDto;
import com.cadence.music.entity.Genre;
import com.cadence.music.entity.LikedTrack;
import com.cadence.music.entity.Track;
import com.cadence.music.entity.User;
import com.cadence.music.repository.LikedTrackRepository;
import com.cadence.music.repository.TrackRepository;
import com.cadence.music.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LikedTrackService {

    private final LikedTrackRepository likedTrackRepository;
    private final UserRepository userRepository;
    private final TrackRepository trackRepository;

    public LikedTrackService(LikedTrackRepository likedTrackRepository,
                             UserRepository userRepository,
                             TrackRepository trackRepository) {
        this.likedTrackRepository = likedTrackRepository;
        this.userRepository = userRepository;
        this.trackRepository = trackRepository;
    }

    @Transactional
    public void likeTrack(String userEmail, Long trackId) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found: " + userEmail));

        Track track = trackRepository.findById(trackId)
                .orElseThrow(() -> new RuntimeException("Track not found with id: " + trackId));

        if (!likedTrackRepository.existsByUserIdAndTrackId(user.getId(), track.getId())) {
            LikedTrack likedTrack = new LikedTrack(user.getId(), track.getId());
            likedTrackRepository.save(likedTrack);
        }
    }

    @Transactional
    public void unlikeTrack(String userEmail, Long trackId) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found: " + userEmail));

        likedTrackRepository.deleteByUserIdAndTrackId(user.getId(), trackId);
    }

    @Transactional(readOnly = true)
    public List<TrackResponseDto> getLikedTracks(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found: " + userEmail));

        return likedTrackRepository.findByUserIdOrderByLikedAtDesc(user.getId())
                .stream()
                .map(lt -> mapTrackToDto(lt.getTrack()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public boolean isTrackLiked(String userEmail, Long trackId) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found: " + userEmail));

        return likedTrackRepository.existsByUserIdAndTrackId(user.getId(), trackId);
    }

    private TrackResponseDto mapTrackToDto(Track track) {
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
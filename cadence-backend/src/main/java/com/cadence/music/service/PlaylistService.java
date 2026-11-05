package com.cadence.music.service;

import com.cadence.music.dto.PlaylistRequestDto;
import com.cadence.music.dto.PlaylistResponseDto;
import com.cadence.music.dto.TrackResponseDto;
import com.cadence.music.entity.Genre;
import com.cadence.music.entity.Playlist;
import com.cadence.music.entity.PlaylistTrack;
import com.cadence.music.entity.Track;
import com.cadence.music.entity.User;
import com.cadence.music.repository.PlaylistRepository;
import com.cadence.music.repository.PlaylistTrackRepository;
import com.cadence.music.repository.TrackRepository;
import com.cadence.music.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final PlaylistTrackRepository playlistTrackRepository;
    private final UserRepository userRepository;
    private final TrackRepository trackRepository;

    public PlaylistService(PlaylistRepository playlistRepository,
                           PlaylistTrackRepository playlistTrackRepository,
                           UserRepository userRepository,
                           TrackRepository trackRepository) {
        this.playlistRepository = playlistRepository;
        this.playlistTrackRepository = playlistTrackRepository;
        this.userRepository = userRepository;
        this.trackRepository = trackRepository;
    }

    @Transactional
    public PlaylistResponseDto createPlaylist(String userEmail, PlaylistRequestDto dto) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found: " + userEmail));

        Playlist playlist = new Playlist();
        playlist.setUser(user);
        playlist.setTitle(dto.getTitle());
        playlist.setDescription(dto.getDescription());
        playlist.setCoverArtUrl(dto.getCoverArtUrl());
        playlist.setIsPublic(dto.getIsPublic() != null ? dto.getIsPublic() : true);

        Playlist saved = playlistRepository.save(playlist);
        return mapToDto(saved);
    }

    @Transactional(readOnly = true)
    public List<PlaylistResponseDto> getUserPlaylists(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found: " + userEmail));

        return playlistRepository.findByUserIdOrderByCreatedAtDesc(user.getId())
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PlaylistResponseDto getPlaylistById(Long id) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Playlist not found with id: " + id));
        return mapToDto(playlist);
    }

    @Transactional
    public PlaylistResponseDto addTrackToPlaylist(Long playlistId, Long trackId, String userEmail) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found with id: " + playlistId));

        if (!playlist.getUser().getEmail().equals(userEmail)) {
            throw new RuntimeException("Unauthorized: You do not own this playlist");
        }

        Track track = trackRepository.findById(trackId)
                .orElseThrow(() -> new RuntimeException("Track not found with id: " + trackId));

        int nextPosition = playlist.getPlaylistTracks().size() + 1;
        PlaylistTrack playlistTrack = new PlaylistTrack(playlist, track, nextPosition);
        playlist.getPlaylistTracks().add(playlistTrack);

        Playlist updated = playlistRepository.save(playlist);
        return mapToDto(updated);
    }

    @Transactional
    public PlaylistResponseDto removeTrackFromPlaylist(Long playlistId, Long trackId, String userEmail) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found with id: " + playlistId));

        if (!playlist.getUser().getEmail().equals(userEmail)) {
            throw new RuntimeException("Unauthorized: You do not own this playlist");
        }

        playlistTrackRepository.deleteByPlaylistIdAndTrackId(playlistId, trackId);
        return getPlaylistById(playlistId);
    }

    @Transactional
    public void deletePlaylist(Long playlistId, String userEmail) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found with id: " + playlistId));

        if (!playlist.getUser().getEmail().equals(userEmail)) {
            throw new RuntimeException("Unauthorized: You do not own this playlist");
        }

        playlistRepository.delete(playlist);
    }

    private PlaylistResponseDto mapToDto(Playlist playlist) {
        PlaylistResponseDto dto = new PlaylistResponseDto();
        dto.setId(playlist.getId());
        dto.setUserId(playlist.getUser().getId());
        dto.setUserDisplayName(playlist.getUser().getDisplayName());
        dto.setTitle(playlist.getTitle());
        dto.setDescription(playlist.getDescription());
        dto.setCoverArtUrl(playlist.getCoverArtUrl());
        dto.setIsPublic(playlist.getIsPublic());
        dto.setCreatedAt(playlist.getCreatedAt());

        List<TrackResponseDto> trackDtos = playlist.getPlaylistTracks().stream()
                .map(pt -> mapTrackToDto(pt.getTrack()))
                .collect(Collectors.toList());

        dto.setTracks(trackDtos);
        return dto;
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
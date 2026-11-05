package com.cadence.music.controller;

import com.cadence.music.dto.AddTrackToPlaylistRequestDto;
import com.cadence.music.dto.PlaylistRequestDto;
import com.cadence.music.dto.PlaylistResponseDto;
import com.cadence.music.service.PlaylistService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/playlists")
public class PlaylistController {

    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @PostMapping
    public ResponseEntity<PlaylistResponseDto> createPlaylist(
            Principal principal,
            @Valid @RequestBody PlaylistRequestDto dto) {
        return ResponseEntity.ok(playlistService.createPlaylist(principal.getName(), dto));
    }

    @GetMapping("/me")
    public ResponseEntity<List<PlaylistResponseDto>> getUserPlaylists(Principal principal) {
        return ResponseEntity.ok(playlistService.getUserPlaylists(principal.getName()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaylistResponseDto> getPlaylistById(@PathVariable Long id) {
        return ResponseEntity.ok(playlistService.getPlaylistById(id));
    }

    @PostMapping("/{id}/tracks")
    public ResponseEntity<PlaylistResponseDto> addTrackToPlaylist(
            @PathVariable Long id,
            Principal principal,
            @Valid @RequestBody AddTrackToPlaylistRequestDto dto) {
        return ResponseEntity.ok(playlistService.addTrackToPlaylist(id, dto.getTrackId(), principal.getName()));
    }

    @DeleteMapping("/{id}/tracks/{trackId}")
    public ResponseEntity<PlaylistResponseDto> removeTrackFromPlaylist(
            @PathVariable Long id,
            @PathVariable Long trackId,
            Principal principal) {
        return ResponseEntity.ok(playlistService.removeTrackFromPlaylist(id, trackId, principal.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlaylist(@PathVariable Long id, Principal principal) {
        playlistService.deletePlaylist(id, principal.getName());
        return ResponseEntity.ok().build();
    }
}
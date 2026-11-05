package com.cadence.music.controller;

import com.cadence.music.dto.TrackResponseDto;
import com.cadence.music.service.LikedTrackService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/favorites")
public class LikedTrackController {

    private final LikedTrackService likedTrackService;

    public LikedTrackController(LikedTrackService likedTrackService) {
        this.likedTrackService = likedTrackService;
    }

    @PostMapping("/tracks/{trackId}")
    public ResponseEntity<Void> likeTrack(@PathVariable Long trackId, Principal principal) {
        likedTrackService.likeTrack(principal.getName(), trackId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/tracks/{trackId}")
    public ResponseEntity<Void> unlikeTrack(@PathVariable Long trackId, Principal principal) {
        likedTrackService.unlikeTrack(principal.getName(), trackId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/tracks")
    public ResponseEntity<List<TrackResponseDto>> getLikedTracks(Principal principal) {
        return ResponseEntity.ok(likedTrackService.getLikedTracks(principal.getName()));
    }

    @GetMapping("/tracks/{trackId}/status")
    public ResponseEntity<Map<String, Boolean>> isTrackLiked(@PathVariable Long trackId, Principal principal) {
        boolean liked = likedTrackService.isTrackLiked(principal.getName(), trackId);
        return ResponseEntity.ok(Map.of("liked", liked));
    }
}
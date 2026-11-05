package com.cadence.music.controller;

import com.cadence.music.dto.TrackCreateRequestDto;
import com.cadence.music.dto.TrackResponseDto;
import com.cadence.music.service.TrackService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tracks")
public class TrackController {

    private final TrackService trackService;

    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }

    @GetMapping
    public ResponseEntity<Page<TrackResponseDto>> getAllTracks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(trackService.getAllTracks(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrackResponseDto> getTrackById(@PathVariable Long id) {
        return ResponseEntity.ok(trackService.getTrackById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<TrackResponseDto>> searchTracks(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(trackService.searchTracks(query, page, size));
    }

    @PostMapping
    public ResponseEntity<TrackResponseDto> createTrack(@Valid @RequestBody TrackCreateRequestDto dto) {
        return ResponseEntity.ok(trackService.createTrack(dto));
    }

    @PostMapping("/{id}/play")
    public ResponseEntity<Void> recordPlay(@PathVariable Long id) {
        trackService.incrementPlayCount(id);
        return ResponseEntity.ok().build();
    }
}
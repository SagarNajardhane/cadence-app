-- Seed Artists
INSERT INTO artists (name, bio, verified) VALUES 
('Solaris Sound', 'Futuristic synthwave and atmospheric soundscapes.', TRUE),
('Echo & Dust', 'Acoustic instrumental and lo-fi warmth.', TRUE);

-- Seed Albums
INSERT INTO albums (artist_id, title, cover_art_url, release_date, album_type) VALUES 
(1, 'Neon Horizon', 'https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?w=500', '2024-01-15', 'ALBUM'),
(2, 'Midnight Coffee', 'https://images.unsplash.com/photo-1518609878373-06d740f60d8b?w=500', '2024-02-01', 'SINGLE');

-- Seed Tracks with Demo Royalty-Free Audio Files
INSERT INTO tracks (album_id, artist_id, title, duration_seconds, audio_url, play_count) VALUES 
(1, 1, 'Cyber Dreams', 210, 'https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3', 1420),
(1, 1, 'Starlight Drive', 195, 'https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3', 890),
(2, 2, 'Rainy Window', 160, 'https://www.soundhelix.com/examples/mp3/SoundHelix-Song-3.mp3', 2300);

-- Map Genres to Tracks
INSERT INTO track_genres (track_id, genre_id) VALUES 
(1, 4), -- Cyber Dreams -> Synthwave
(2, 1), -- Starlight Drive -> Electronic
(3, 3); -- Rainy Window -> Lo-Fi
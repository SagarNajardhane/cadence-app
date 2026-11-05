import { useEffect, useState } from 'react';
import { useAudio } from '../../context/AudioContext';
import { useAuth } from '../../context/AuthContext';
import apiClient from '../../api/axios';
import { Play, Pause, SkipBack, SkipForward, Volume2, VolumeX, Music, Heart } from 'lucide-react';

const formatTime = (seconds) => {
  if (isNaN(seconds)) return '0:00';
  const mins = Math.floor(seconds / 60);
  const secs = Math.floor(seconds % 60);
  return `${mins}:${secs < 10 ? '0' : ''}${secs}`;
};

const Player = () => {
  const {
    currentTrack,
    isPlaying,
    progress,
    duration,
    volume,
    isMuted,
    togglePlay,
    handleNext,
    handlePrev,
    seek,
    changeVolume,
    toggleMute,
  } = useAudio();

  const { isAuthenticated } = useAuth();
  const [isLiked, setIsLiked] = useState(false);

  // Check if current track is liked whenever track changes
  useEffect(() => {
    if (!currentTrack || !isAuthenticated) {
      setIsLiked(false);
      return;
    }

    const checkLikedStatus = async () => {
      try {
        const res = await apiClient.get(`/favorites/tracks/${currentTrack.id}/status`);
        setIsLiked(res.data.liked);
      } catch (err) {
        console.error('Failed to check liked status:', err);
      }
    };

    checkLikedStatus();
  }, [currentTrack, isAuthenticated]);

  const toggleFavorite = async () => {
    if (!currentTrack || !isAuthenticated) return;
    try {
      if (isLiked) {
        await apiClient.delete(`/favorites/tracks/${currentTrack.id}`);
        setIsLiked(false);
      } else {
        await apiClient.post(`/favorites/tracks/${currentTrack.id}`);
        setIsLiked(true);
      }
    } catch (err) {
      console.error('Failed to toggle favorite:', err);
    }
  };

  return (
    <footer className="player-bar">
      <div className="player-track-info">
        {currentTrack ? (
          <>
            {currentTrack.coverArtUrl ? (
              <img src={currentTrack.coverArtUrl} alt={currentTrack.title} className="track-artwork" />
            ) : (
              <div className="track-artwork" style={{ display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
                <Music size={24} color="#94A3B8" />
              </div>
            )}
            <div className="track-details">
              <span className="track-title">{currentTrack.title}</span>
              <span className="track-artist">{currentTrack.artistName || 'Unknown Artist'}</span>
            </div>
            {isAuthenticated && (
              <button
                onClick={toggleFavorite}
                style={{ background: 'transparent', border: 'none', cursor: 'pointer', marginLeft: '0.5rem', padding: '4px' }}
                title={isLiked ? 'Remove from favorites' : 'Add to favorites'}
              >
                <Heart
                  size={20}
                  fill={isLiked ? '#06B6D4' : 'none'}
                  color={isLiked ? '#06B6D4' : '#94A3B8'}
                  style={{ transition: 'all 0.2s ease' }}
                />
              </button>
            )}
          </>
        ) : (
          <div className="track-details">
            <span className="track-title" style={{ color: 'var(--text-muted)' }}>No track selected</span>
          </div>
        )}
      </div>

      <div className="player-controls">
        <div className="control-buttons">
          <button className="btn-control" onClick={handlePrev} disabled={!currentTrack}>
            <SkipBack size={20} />
          </button>
          <button className="btn-play" onClick={togglePlay} disabled={!currentTrack}>
            {isPlaying ? <Pause size={20} /> : <Play size={20} style={{ marginLeft: '2px' }} />}
          </button>
          <button className="btn-control" onClick={handleNext} disabled={!currentTrack}>
            <SkipForward size={20} />
          </button>
        </div>

        <div className="progress-container">
          <span>{formatTime(progress)}</span>
          <input
            type="range"
            className="progress-bar"
            min="0"
            max={duration || 100}
            value={progress}
            onChange={(e) => seek(Number(e.target.value))}
            disabled={!currentTrack}
          />
          <span>{formatTime(duration)}</span>
        </div>
      </div>

      <div className="player-volume">
        <button className="btn-control" onClick={toggleMute}>
          {isMuted || volume === 0 ? <VolumeX size={20} /> : <Volume2 size={20} />}
        </button>
        <input
          type="range"
          className="volume-slider"
          min="0"
          max="1"
          step="0.01"
          value={isMuted ? 0 : volume}
          onChange={(e) => changeVolume(Number(e.target.value))}
        />
      </div>
    </footer>
  );
};

export default Player;
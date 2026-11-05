import { useEffect, useState } from 'react';
import apiClient from '../api/axios';
import { useAudio } from '../context/AudioContext';
import { useAuth } from '../context/AuthContext';
import { Play, Pause, Music, Heart } from 'lucide-react';
import './Pages.css';

const Favorites = () => {
  const [likedTracks, setLikedTracks] = useState([]);
  const [loading, setLoading] = useState(true);
  const { currentTrack, isPlaying, playTrack, togglePlay } = useAudio();
  const { isAuthenticated } = useAuth();

  const fetchFavorites = async () => {
    if (!isAuthenticated) {
      setLoading(false);
      return;
    }
    try {
      const res = await apiClient.get('/favorites/tracks');
      setLikedTracks(res.data);
    } catch (err) {
      console.error('Failed to fetch liked tracks:', err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchFavorites();
  }, [isAuthenticated]);

  const handleUnlike = async (e, trackId) => {
    e.stopPropagation();
    try {
      await apiClient.delete(`/favorites/tracks/${trackId}`);
      setLikedTracks((prev) => prev.filter((t) => t.id !== trackId));
    } catch (err) {
      console.error('Failed to unlike track:', err);
    }
  };

  return (
    <div className="page-container">
      <header className="page-header">
        <h1>Favorites</h1>
        <p>Your collection of liked songs on Cadence.</p>
      </header>

      {!isAuthenticated ? (
        <p style={{ color: 'var(--text-secondary)' }}>Please log in to view your favorite tracks.</p>
      ) : loading ? (
        <p style={{ color: 'var(--text-secondary)' }}>Loading favorites...</p>
      ) : likedTracks.length === 0 ? (
        <p style={{ color: 'var(--text-secondary)' }}>You haven't liked any tracks yet. Click the heart icon on any song to save it here!</p>
      ) : (
        <div className="track-grid">
          {likedTracks.map((track) => {
            const isSelected = currentTrack?.id === track.id;
            return (
              <div key={track.id} className="track-card" onClick={() => (isSelected ? togglePlay() : playTrack(track, likedTracks))}>
                <div className="card-image-wrapper">
                  {track.coverArtUrl ? (
                    <img src={track.coverArtUrl} alt={track.title} className="card-image" />
                  ) : (
                    <div className="card-image-placeholder">
                      <Music size={40} color="#64748B" />
                    </div>
                  )}
                  <button className="card-play-btn">
                    {isSelected && isPlaying ? <Pause size={22} /> : <Play size={22} style={{ marginLeft: '2px' }} />}
                  </button>
                </div>
                <div className="card-info" style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                  <div style={{ flex: 1, overflow: 'hidden' }}>
                    <span className="card-title">{track.title}</span>
                    <span className="card-subtitle">{track.artistName || 'Cadence Artist'}</span>
                  </div>
                  <button
                    onClick={(e) => handleUnlike(e, track.id)}
                    style={{ background: 'transparent', border: 'none', cursor: 'pointer', padding: '4px' }}
                    title="Remove from favorites"
                  >
                    <Heart size={18} fill="#06B6D4" color="#06B6D4" />
                  </button>
                </div>
              </div>
            );
          })}
        </div>
      )}
    </div>
  );
};

export default Favorites;
import { useEffect, useState } from 'react';
import apiClient from '../api/axios';
import { useAudio } from '../context/AudioContext';
import { useAuth } from '../context/AuthContext';
import { Play, Pause, Music, Heart, Plus, X } from 'lucide-react';
import './Pages.css';

const Discover = () => {
  const [tracks, setTracks] = useState([]);
  const [likedTrackIds, setLikedTrackIds] = useState(new Set());
  const [loading, setLoading] = useState(true);
  const [showAddModal, setShowModal] = useState(false);

  // Form state
  const [title, setTitle] = useState('');
  const [artistName, setArtistName] = useState('');
  const [audioUrl, setAudioUrl] = useState('');
  const [coverArtUrl, setCoverArtUrl] = useState('');
  const [durationSeconds, setDurationSeconds] = useState(195);

  const { currentTrack, isPlaying, playTrack, togglePlay } = useAudio();
  const { isAuthenticated } = useAuth();

  const fetchDiscoverData = async () => {
    try {
      const res = await apiClient.get('/tracks');
      const trackList = res.data.content || res.data;
      setTracks(trackList);

      if (isAuthenticated) {
        const favRes = await apiClient.get('/favorites/tracks');
        const favIds = new Set(favRes.data.map((t) => t.id));
        setLikedTrackIds(favIds);
      }
    } catch (err) {
      console.error('Failed to fetch discover data:', err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchDiscoverData();
  }, [isAuthenticated]);

  const handlePlayCard = (track) => {
    if (currentTrack?.id === track.id) {
      togglePlay();
    } else {
      playTrack(track, tracks);
    }
  };

  const toggleFavorite = async (e, trackId) => {
    e.stopPropagation();
    if (!isAuthenticated) return;

    try {
      if (likedTrackIds.has(trackId)) {
        await apiClient.delete(`/favorites/tracks/${trackId}`);
        setLikedTrackIds((prev) => {
          const next = new Set(prev);
          next.delete(trackId);
          return next;
        });
      } else {
        await apiClient.post(`/favorites/tracks/${trackId}`);
        setLikedTrackIds((prev) => new Set(prev).add(trackId));
      }
    } catch (err) {
      console.error('Failed to toggle favorite:', err);
    }
  };

  const handleAddTrack = async (e) => {
    e.preventDefault();
    if (!title || !artistName || !audioUrl) return;

    try {
      const res = await apiClient.post('/tracks', {
        title,
        artistName,
        audioUrl,
        coverArtUrl: coverArtUrl || null,
        durationSeconds: Number(durationSeconds) || 180,
      });

      setTracks((prev) => [res.data, ...prev]);
      setTitle('');
      setArtistName('');
      setAudioUrl('');
      setCoverArtUrl('');
      setShowModal(false);
    } catch (err) {
      console.error('Failed to add track:', err);
      alert('Failed to add song. Please check the backend connection.');
    }
  };

  return (
    <div className="page-container">
      <div className="page-header-row">
        <header className="page-header">
          <h1>Discover</h1>
          <p>Explore high-fidelity tracks curated for you on Cadence.</p>
        </header>
        {isAuthenticated && (
          <button
            className="btn-submit"
            style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}
            onClick={() => setShowModal(true)}
          >
            <Plus size={18} /> Add Song
          </button>
        )}
      </div>

      {showAddModal && (
        <div className="modal-backdrop" onClick={() => setShowModal(false)}>
          <div className="modal-content" onClick={(e) => e.stopPropagation()}>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
              <h3 style={{ fontSize: '1.25rem' }}>Add New Song to Cadence</h3>
              <button
                onClick={() => setShowModal(false)}
                style={{ background: 'transparent', border: 'none', color: 'var(--text-muted)', cursor: 'pointer' }}
              >
                <X size={20} />
              </button>
            </div>
            <form onSubmit={handleAddTrack} style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
              <input
                type="text"
                className="form-input"
                placeholder="Song Title (e.g. Tum Mere Na Huye)"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
                required
              />
              <input
                type="text"
                className="form-input"
                placeholder="Artist Name (e.g. Madhubanti Bagchi)"
                value={artistName}
                onChange={(e) => setArtistName(e.target.value)}
                required
              />
              <input
                type="url"
                className="form-input"
                placeholder="Audio MP3 URL"
                value={audioUrl}
                onChange={(e) => setAudioUrl(e.target.value)}
                required
              />
              <input
                type="url"
                className="form-input"
                placeholder="Cover Art URL (optional)"
                value={coverArtUrl}
                onChange={(e) => setCoverArtUrl(e.target.value)}
              />
              <input
                type="number"
                className="form-input"
                placeholder="Duration in seconds (e.g. 195)"
                value={durationSeconds}
                onChange={(e) => setDurationSeconds(e.target.value)}
              />
              <div style={{ display: 'flex', gap: '0.75rem', justifyContent: 'flex-end', marginTop: '0.5rem' }}>
                <button type="button" className="btn-auth btn-secondary" onClick={() => setShowModal(false)}>
                  Cancel
                </button>
                <button type="submit" className="btn-submit">
                  Add Song
                </button>
              </div>
            </form>
          </div>
        </div>
      )}

      {loading ? (
        <p style={{ color: 'var(--text-secondary)' }}>Loading tracks...</p>
      ) : (
        <div className="track-grid">
          {tracks.map((track) => {
            const isSelected = currentTrack?.id === track.id;
            const isLiked = likedTrackIds.has(track.id);
            return (
              <div key={track.id} className="track-card" onClick={() => handlePlayCard(track)}>
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
                <div className="card-info">
                  <div className="card-text">
                    <span className="card-title">{track.title}</span>
                    <span className="card-subtitle">{track.artistName || 'Cadence Artist'}</span>
                  </div>
                  {isAuthenticated && (
                    <button
                      onClick={(e) => toggleFavorite(e, track.id)}
                      style={{ background: 'transparent', border: 'none', cursor: 'pointer', padding: '4px' }}
                      title={isLiked ? 'Remove from favorites' : 'Add to favorites'}
                    >
                      <Heart
                        size={18}
                        fill={isLiked ? '#06B6D4' : 'none'}
                        color={isLiked ? '#06B6D4' : '#94A3B8'}
                      />
                    </button>
                  )}
                </div>
              </div>
            );
          })}
        </div>
      )}
    </div>
  );
};

export default Discover;
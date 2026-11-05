import { useState } from 'react';
import apiClient from '../api/axios';
import { useAudio } from '../context/AudioContext';
import { Search as SearchIcon, Play, Pause, Music } from 'lucide-react';
import './Pages.css';

const Search = () => {
  const [query, setQuery] = useState('');
  const [results, setResults] = useState([]);
  const [loading, setLoading] = useState(false);
  const { currentTrack, isPlaying, playTrack, togglePlay } = useAudio();

  const handleSearch = async (e) => {
    e.preventDefault();
    if (!query.trim()) return;
    setLoading(true);
    try {
      const res = await apiClient.get(`/tracks/search?query=${encodeURIComponent(query)}`);
      setResults(res.data.content || res.data);
    } catch (err) {
      console.error('Search failed:', err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="page-container">
      <header className="page-header">
        <h1>Search</h1>
        <p>Search for tracks, artists, or genres on Cadence.</p>
      </header>

      <form onSubmit={handleSearch} style={{ display: 'flex', gap: '1rem', maxWidth: '500px' }}>
        <input
          type="text"
          className="form-input"
          style={{ flex: 1 }}
          placeholder="Search tracks..."
          value={query}
          onChange={(e) => setQuery(e.target.value)}
        />
        <button type="submit" className="btn-submit" style={{ padding: '0.75rem 1.5rem', marginTop: 0 }}>
          <SearchIcon size={18} />
        </button>
      </form>

      {loading && <p style={{ color: 'var(--text-secondary)' }}>Searching...</p>}

      {results.length > 0 && (
        <div className="track-grid">
          {results.map((track) => {
            const isSelected = currentTrack?.id === track.id;
            return (
              <div key={track.id} className="track-card" onClick={() => (isSelected ? togglePlay() : playTrack(track, results))}>
                <div className="card-image-wrapper">
                  <div className="card-image-placeholder">
                    <Music size={40} color="#64748B" />
                  </div>
                  <button className="card-play-btn">
                    {isSelected && isPlaying ? <Pause size={22} /> : <Play size={22} style={{ marginLeft: '2px' }} />}
                  </button>
                </div>
                <div className="card-info">
                  <span className="card-title">{track.title}</span>
                  <span className="card-subtitle">{track.artistName || 'Artist'}</span>
                </div>
              </div>
            );
          })}
        </div>
      )}
    </div>
  );
};

export default Search;
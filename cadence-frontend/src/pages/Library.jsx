import { useEffect, useState } from 'react';
import apiClient from '../api/axios';
import { useAuth } from '../context/AuthContext';
import { Plus, Disc, Trash2 } from 'lucide-react';
import './Pages.css';

const Library = () => {
  const [playlists, setPlaylists] = useState([]);
  const [loading, setLoading] = useState(true);
  const [newTitle, setNewTitle] = useState('');
  const [newDesc, setNewDesc] = useState('');
  const [showModal, setShowModal] = useState(false);
  const { isAuthenticated } = useAuth();

  const fetchPlaylists = async () => {
    if (!isAuthenticated) {
      setLoading(false);
      return;
    }
    try {
      const res = await apiClient.get('/playlists/me');
      setPlaylists(res.data);
    } catch (err) {
      console.error('Failed to fetch playlists:', err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchPlaylists();
  }, [isAuthenticated]);

  const handleCreatePlaylist = async (e) => {
    e.preventDefault();
    if (!newTitle.trim()) return;
    try {
      const res = await apiClient.post('/playlists', { title: newTitle, description: newDesc });
      setPlaylists([res.data, ...playlists]);
      setNewTitle('');
      setNewDesc('');
      setShowModal(false);
    } catch (err) {
      console.error('Failed to create playlist:', err);
    }
  };

  const handleDeletePlaylist = async (playlistId) => {
    try {
      await apiClient.delete(`/playlists/${playlistId}`);
      setPlaylists((prev) => prev.filter((p) => p.id !== playlistId));
    } catch (err) {
      console.error('Failed to delete playlist:', err);
    }
  };

  return (
    <div className="page-container">
      <header className="page-header" style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        <div>
          <h1>Your Library</h1>
          <p>Create and manage your custom music playlists.</p>
        </div>
        {isAuthenticated && (
          <button className="btn-submit" style={{ display: 'flex', alignItems: 'center', gap: '0.5rem', marginTop: 0 }} onClick={() => setShowModal(true)}>
            <Plus size={18} /> New Playlist
          </button>
        )}
      </header>

      {showModal && (
        <div style={{ background: 'var(--bg-surface)', border: '1px solid var(--border-color)', borderRadius: '12px', padding: '1.5rem', maxWidth: '450px' }}>
          <h3 style={{ marginBottom: '1rem' }}>Create Playlist</h3>
          <form onSubmit={handleCreatePlaylist} style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
            <input
              type="text"
              className="form-input"
              placeholder="Playlist title"
              value={newTitle}
              onChange={(e) => setNewTitle(e.target.value)}
              required
            />
            <input
              type="text"
              className="form-input"
              placeholder="Description (optional)"
              value={newDesc}
              onChange={(e) => setNewDesc(e.target.value)}
            />
            <div style={{ display: 'flex', gap: '0.75rem', justifyContent: 'flex-end' }}>
              <button type="button" className="btn-auth btn-secondary" onClick={() => setShowModal(false)}>Cancel</button>
              <button type="submit" className="btn-submit" style={{ marginTop: 0 }}>Create</button>
            </div>
          </form>
        </div>
      )}

      {!isAuthenticated ? (
        <p style={{ color: 'var(--text-secondary)' }}>Please log in to manage your music library.</p>
      ) : loading ? (
        <p style={{ color: 'var(--text-secondary)' }}>Loading library...</p>
      ) : playlists.length === 0 ? (
        <p style={{ color: 'var(--text-secondary)' }}>No playlists yet. Click "New Playlist" above to create one!</p>
      ) : (
        <div className="track-grid">
          {playlists.map((pl) => (
            <div key={pl.id} className="track-card">
              <div className="card-image-wrapper">
                <div className="card-image-placeholder">
                  <Disc size={40} color="#6366F1" />
                </div>
              </div>
              <div className="card-info" style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <div>
                  <span className="card-title">{pl.title}</span>
                  <span className="card-subtitle">{pl.tracks?.length || 0} tracks</span>
                </div>
                <button
                  onClick={() => handleDeletePlaylist(pl.id)}
                  style={{ background: 'transparent', border: 'none', color: 'var(--text-muted)', cursor: 'pointer' }}
                  title="Delete playlist"
                >
                  <Trash2 size={16} />
                </button>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default Library;
import { createContext, useContext, useState, useRef, useEffect } from 'react';
import apiClient from '../api/axios';

const AudioContext = createContext();

export const AudioProvider = ({ children }) => {
  const audioRef = useRef(new Audio());
  const [currentTrack, setCurrentTrack] = useState(null);
  const [playlist, setPlaylist] = useState([]);
  const [currentIndex, setCurrentIndex] = useState(-1);
  const [isPlaying, setIsPlaying] = useState(false);
  const [progress, setProgress] = useState(0);
  const [duration, setDuration] = useState(0);
  const [volume, setVolume] = useState(0.8);
  const [isMuted, setIsMuted] = useState(false);

  useEffect(() => {
    const audio = audioRef.current;

    const handleTimeUpdate = () => {
      setProgress(audio.currentTime);
    };

    const handleLoadedMetadata = () => {
      setDuration(audio.duration);
    };

    const handleEnded = () => {
      handleNext();
    };

    audio.addEventListener('timeupdate', handleTimeUpdate);
    audio.addEventListener('loadedmetadata', handleLoadedMetadata);
    audio.addEventListener('ended', handleEnded);

    return () => {
      audio.removeEventListener('timeupdate', handleTimeUpdate);
      audio.removeEventListener('loadedmetadata', handleLoadedMetadata);
      audio.removeEventListener('ended', handleEnded);
    };
  }, [currentIndex, playlist]);

  const playTrack = (track, newPlaylist = [track]) => {
    const index = newPlaylist.findIndex((t) => t.id === track.id);
    setPlaylist(newPlaylist);
    setCurrentIndex(index >= 0 ? index : 0);
    setCurrentTrack(track);

    if (track.audioUrl) {
      audioRef.current.src = track.audioUrl;
      audioRef.current.volume = isMuted ? 0 : volume;
      audioRef.current.play().then(() => {
        setIsPlaying(true);
        // Record play count on backend database
        apiClient.post(`/tracks/${track.id}/play`).catch((err) => console.error('Play count update error:', err));
      }).catch(console.error);
    }
  };

  const togglePlay = () => {
    if (!currentTrack) return;
    if (isPlaying) {
      audioRef.current.pause();
      setIsPlaying(false);
    } else {
      audioRef.current.play().then(() => setIsPlaying(true)).catch(console.error);
    }
  };

  const handleNext = () => {
    if (playlist.length === 0 || currentIndex === -1) return;
    const nextIndex = (currentIndex + 1) % playlist.length;
    playTrack(playlist[nextIndex], playlist);
  };

  const handlePrev = () => {
    if (playlist.length === 0 || currentIndex === -1) return;
    const prevIndex = (currentIndex - 1 + playlist.length) % playlist.length;
    playTrack(playlist[prevIndex], playlist);
  };

  const seek = (seconds) => {
    if (!currentTrack) return;
    audioRef.current.currentTime = seconds;
    setProgress(seconds);
  };

  const changeVolume = (val) => {
    setVolume(val);
    setIsMuted(val === 0);
    audioRef.current.volume = val;
  };

  const toggleMute = () => {
    if (isMuted) {
      setIsMuted(false);
      audioRef.current.volume = volume;
    } else {
      setIsMuted(true);
      audioRef.current.volume = 0;
    }
  };

  return (
    <AudioContext.Provider
      value={{
        currentTrack,
        playlist,
        isPlaying,
        progress,
        duration,
        volume,
        isMuted,
        playTrack,
        togglePlay,
        handleNext,
        handlePrev,
        seek,
        changeVolume,
        toggleMute,
      }}
    >
      {children}
    </AudioContext.Provider>
  );
};

export const useAudio = () => useContext(AudioContext);
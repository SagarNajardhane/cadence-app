import { createContext, useContext, useState, useEffect } from 'react';
import apiClient from '../api/axios';

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(() => {
    const savedUser = localStorage.getItem('cadence_user');
    return savedUser ? JSON.parse(savedUser) : null;
  });
  const [token, setToken] = useState(() => localStorage.getItem('cadence_token') || null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchCurrentUser = async () => {
      if (token) {
        try {
          const res = await apiClient.get('/auth/me');
          setUser(res.data);
          localStorage.setItem('cadence_user', JSON.stringify(res.data));
        } catch (err) {
          console.error('Failed to restore session:', err);
          logout();
        }
      }
      setLoading(false);
    };

    fetchCurrentUser();
  }, [token]);

  const login = async (email, password) => {
    const response = await apiClient.post('/auth/login', { email, password });
    const { token: jwtToken, ...userData } = response.data;
    setToken(jwtToken);
    setUser(userData);
    localStorage.setItem('cadence_token', jwtToken);
    localStorage.setItem('cadence_user', JSON.stringify(userData));
    return response.data;
  };

  const register = async (displayName, email, password) => {
    const response = await apiClient.post('/auth/register', { displayName, email, password });
    const { token: jwtToken, ...userData } = response.data;
    setToken(jwtToken);
    setUser(userData);
    localStorage.setItem('cadence_token', jwtToken);
    localStorage.setItem('cadence_user', JSON.stringify(userData));
    return response.data;
  };

  const logout = () => {
    setToken(null);
    setUser(null);
    localStorage.removeItem('cadence_token');
    localStorage.removeItem('cadence_user');
  };

  return (
    <AuthContext.Provider value={{ user, token, isAuthenticated: !!token, loading, login, register, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
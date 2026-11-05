import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import { AudioProvider } from './context/AudioContext';
import Layout from './components/layout/Layout';
import Discover from './pages/Discover';
import Search from './pages/Search';
import Library from './pages/Library';
import Favorites from './pages/Favorites';
import Login from './pages/Login';
import Register from './pages/Register';

function App() {
  return (
    <BrowserRouter>
      <AuthProvider>
        <AudioProvider>
          <Routes>
            <Route path="/" element={<Layout />}>
              <Route index element={<Discover />} />
              <Route path="search" element={<Search />} />
              <Route path="library" element={<Library />} />
              <Route path="favorites" element={<Favorites />} />
              <Route path="login" element={<Login />} />
              <Route path="register" element={<Register />} />
            </Route>
          </Routes>
        </AudioProvider>
      </AuthProvider>
    </BrowserRouter>
  );
}

export default App;
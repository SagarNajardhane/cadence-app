import { NavLink } from 'react-router-dom';
import { Home, Search, Library, Heart, Radio } from 'lucide-react';

const Sidebar = () => {
  return (
    <aside className="sidebar">
      <div className="brand-logo">
        <div className="brand-icon">
          <Radio size={20} />
        </div>
        <span className="brand-name">Cadence</span>
      </div>

      <nav className="nav-group">
        <NavLink to="/" className={({ isActive }) => `nav-item ${isActive ? 'active' : ''}`}>
          <Home size={20} />
          <span>Discover</span>
        </NavLink>

        <NavLink to="/search" className={({ isActive }) => `nav-item ${isActive ? 'active' : ''}`}>
          <Search size={20} />
          <span>Search</span>
        </NavLink>

        <NavLink to="/library" className={({ isActive }) => `nav-item ${isActive ? 'active' : ''}`}>
          <Library size={20} />
          <span>Your Library</span>
        </NavLink>

        <NavLink to="/favorites" className={({ isActive }) => `nav-item ${isActive ? 'active' : ''}`}>
          <Heart size={20} />
          <span>Favorites</span>
        </NavLink>
      </nav>
    </aside>
  );
};

export default Sidebar;
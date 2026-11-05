import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';
import { User, LogOut } from 'lucide-react';

const Navbar = () => {
  const { user, isAuthenticated, logout } = useAuth();
  const navigate = useNavigate();

  return (
    <header className="navbar">
      <div className="nav-left">
        {/* Navigation placeholder or search quick-trigger */}
      </div>

      <div className="nav-right">
        {isAuthenticated ? (
          <>
            <div className="user-badge">
              <User size={16} color="#06B6D4" />
              <span>{user?.displayName || 'User'}</span>
            </div>
            <button className="btn-auth btn-secondary" onClick={logout} title="Sign Out">
              <LogOut size={16} />
            </button>
          </>
        ) : (
          <>
            <button className="btn-auth btn-secondary" onClick={() => navigate('/login')}>
              Log In
            </button>
            <button className="btn-auth btn-primary" onClick={() => navigate('/register')}>
              Sign Up
            </button>
          </>
        )}
      </div>
    </header>
  );
};

export default Navbar;
import { Outlet } from 'react-router-dom';
import Sidebar from './Sidebar';
import Navbar from './Navbar';
import Player from './Player';
import './Layout.css';

const Layout = () => {
  return (
    <div className="app-layout">
      <div className="main-container">
        <Sidebar />
        <div className="content-area">
          <Navbar />
          <main className="page-view">
            <Outlet />
          </main>
        </div>
      </div>
      <Player />
    </div>
  );
};

export default Layout;
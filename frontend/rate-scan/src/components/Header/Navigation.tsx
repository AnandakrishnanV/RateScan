import { Link } from 'react-router-dom';

const Navigation = () => {
  return (
    <nav className="d-flex align-items-center">
            <Link to="/" className="nav-link me-5">
              Compare
            </Link>
            <Link to="/about" className="nav-link me-3">
              About
            </Link>
          </nav>
  );
};

export default Navigation;
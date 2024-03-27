import './Header.scss'
import ratescanLogo from '../../assets/logos/Ratescan.png'
import Navigation from './Navigation'
import { Link } from 'react-router-dom'

const Header = () => {
  return (
    <header className="header shadow-sm py-1">
      <div className="container px-3">
        <div className="d-flex justify-content-between align-items-center">
          <div className="d-flex align-items-center">
            <Link
              to="/"
              className="d-flex align-items-center text-decoration-none"
            >
              <img
                src={ratescanLogo}
                alt="Ratescan logo"
                className="logo me-3"
              />
              <h1 className="site-name mb-0 h2">
                <span>Rate</span>
                <span className="site-name-scan">Scan</span>
              </h1>
            </Link>
          </div>
          <Navigation />
        </div>
      </div>
    </header>
  )
}

export default Header

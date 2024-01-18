import React from 'react'
import { Link } from 'react-router-dom'
import './Header.scss'
import reactLogo from '../../assets/react.svg'
import Navigation from './Navigation'

const Header = () => {
  return (
    <header className="header shadow-sm py-5">
      <div className="container px-3">
        <div className="d-flex justify-content-between align-items-center">
          <div className="d-flex align-items-center">
            <img src={reactLogo} alt="Ratescan logo" className="logo me-3" />
            <h1 className="site-name mb-0 h2">Ratescan</h1>
          </div>
          <Navigation />
        </div>
      </div>
    </header>
  )
}

export default Header

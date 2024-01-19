import './Footer.scss'


const Footer = () => {
    return (
      <footer className="footer">
        <div className="container">
          <div className="footer-content">
            <div className="logo-container">
              {/* Replace with your logo */}
              <img src="/path-to-logo.svg" alt="Company Logo" />
            </div>
            <div className="footer-links">
              {/* Replace with your actual links */}
              <a href="/about">About Us</a>
              <a href="/services">Services</a>
              <a href="/contact">Contact</a>
            </div>
            <div className="footer-info">
              {/* Replace with your company info */}
              <p>&copy; 2024 Company Name. All rights reserved.</p>
            </div>
          </div>
        </div>
      </footer>
    );
  };
  
  export default Footer;
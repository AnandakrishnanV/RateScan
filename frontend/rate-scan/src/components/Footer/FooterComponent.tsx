import './FooterComponent.scss';

const FooterComponent = () => {
  return (
    <footer className="site-footer">
      <div className="footer-content container">
        <div className="footer-links">
          <a href="/about-us">About Us</a> |
          <a href="#how-it-works">How It Works</a> |
          <a href="/contact-us">Contact Us</a> |
          <a href="/faqs">FAQs</a> |
          <a href="/privacy">Privacy Policy</a> |
          <a href="/terms-of-service">Terms of Service</a>
        </div>
        <div className="footer-copyright">
          &copy; {new Date().getFullYear()} AK. All rights reserved.
        </div>
        <div className="footer-extra-text">
          Your trusted partner in currency exchange and financial decisions.
        </div>
      </div>
      
    </footer>
  );
};

export default FooterComponent;
import { BrowserRouter, Routes } from 'react-router-dom'
import './App.scss'
import Footer from './components/Footer/Footer'
import Header from './components/Header/Header'
import HeroComponent from './components/Hero/HeroComponent'
import CurrencyComponent from './components/Currency/CurrencyComponent'

const App = () => {
  return (
    <div className="rate-scan-app">
      <BrowserRouter>
        <Header />
        <main className="site-container">
          <HeroComponent />
          <CurrencyComponent />
          <Routes>
            {/* <Route path="/" element={<LoginComponent />}></Route> */}
          </Routes>
        </main>
        <Footer />
      </BrowserRouter>
    </div>
  )
}

export default App

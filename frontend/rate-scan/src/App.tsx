import { BrowserRouter, Route, Routes } from 'react-router-dom'
import './App.scss'
import CurrencyComponent from './components/Currency/CurrencyComponent'
import Footer from './components/Footer/Footer'
import Header from './components/Header/Header'
import HeroComponent from './components/Hero/HeroComponent'
import ExchangeRateTable from './components/ExchangeRate/ExchangeRateTable'

const App = () => {
  return (
    <div className="rate-scan-app">
      <BrowserRouter>
        <Header />
        <main className="site-container">
          <Routes>
            <Route
              path="/"
              element={
                <div>
                  <HeroComponent />
                  <CurrencyComponent />
                </div>
              }
            ></Route>
            <Route
              path="/exchange-rates"
              element={
                <div>
                  <ExchangeRateTable />
                </div>
              }
            ></Route>
          </Routes>
        </main>
        <Footer />
      </BrowserRouter>
    </div>
  )
}

export default App

import { BrowserRouter, Route, Routes } from 'react-router-dom'
import './App.scss'
import './colors.css'
import CurrencyComponent from './components/Currency/CurrencyComponent'
import FooterComponent from './components/Footer/FooterComponent'
import Header from './components/Header/Header'
import HeroComponent from './components/Hero/HeroComponent'
import ExchangeRateTable from './components/ExchangeRate/ExchangeRateTable'
import QuotesTable from './components/Quotes/QuotesTable'
import MissionComponent from './components/Mission/MissionComponent'
import HowItWorksComponent from './components/HowItWorks/HowItWorksComponent'
import PrivacyPolicy from './components/Pages/PrivacyPolicy'
import TermsOfService from './components/Pages/TermsOfService'

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
                  <MissionComponent />
                  <HowItWorksComponent />
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
            <Route
              path="/quotes"
              element={
                <div>
                  <QuotesTable />
                  <MissionComponent />
                  <HowItWorksComponent />
                </div>
              }
            ></Route>
            <Route
              path="/privacy"
              element={
                <div>
                  <PrivacyPolicy />
                </div>
              }
            ></Route>
            <Route
              path="/terms-of-service"
              element={
                <div>
                  <TermsOfService />
                </div>
              }
            ></Route>
          </Routes>
        </main>
        <FooterComponent />
      </BrowserRouter>
    </div>
  )
}

export default App

import { useState } from 'react'
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.scss'
import Header from './components/Header/Header'
import HeroComponent from './components/Hero/HeroComponent'

const App = () => {
  const [count, setCount] = useState(0)

  return (
    <div className="rate-scan-app">
      <BrowserRouter>
      <Header />
      <HeroComponent />
        <Routes>
          {/* <Route path="/" element={<LoginComponent />}></Route> */}
        </Routes>
      </BrowserRouter>
    </div>
  )
}

export default App

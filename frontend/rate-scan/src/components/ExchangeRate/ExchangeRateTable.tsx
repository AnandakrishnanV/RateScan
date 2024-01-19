import { useLocation } from 'react-router-dom'
import './ExchangeRateTable.scss'
import ExchangeRateCard from './ExchangeRateCard'

const ExchangeRateTable = () => {
  const location = useLocation()
  const { exchangeRates } = location.state

  return (
    <div className="container mt-4">
      {exchangeRates.map((rateData: { id: any }) => (
        <ExchangeRateCard key={rateData.id} rateData={rateData} />
      ))}
    </div>
  )
}

export default ExchangeRateTable
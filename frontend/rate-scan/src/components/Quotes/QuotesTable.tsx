import { useLocation } from 'react-router-dom'
import './QuotesTable.scss'
import QuotesCard from './QuotesCard'

const QuotesTable = () => {
  const location = useLocation()
  const { quotes } = location.state

  return (
    <div className="container mt-4">
      {quotes.map((quoteData: { id: any }) => (
        <QuotesCard key={quoteData.id} quoteData={quoteData} />
      ))}
    </div>
  )
}

export default QuotesTable

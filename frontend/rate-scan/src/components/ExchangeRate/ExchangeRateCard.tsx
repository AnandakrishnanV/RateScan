import './ExchangeRateCard.scss'
import providerLogosJson from '../../services/providerLogos'
import { timeAgo } from '../../services/dateUtilService'

interface ProviderLogos {
  [key: string]: string;
}

const providerLogos: ProviderLogos = providerLogosJson;

const ExchangeRateCard = ({ rateData }: { [key: string]: any; }) => {

  const logoUrl = providerLogos[rateData.providerName];

  console.log(logoUrl)

  return (
    <div className="card exchange-rate-card mb-3">
      <div className="card-body">
        <div className="d-flex justify-content-between align-items-center">
          {/* <div className="rate-score">
              <span className="badge bg-success">8</span>
              <span className="provider-name">{rateData.providerName}</span>
            </div> */}
          <div className="rate-logo">
            <img src={logoUrl} alt={`${rateData.providerName} logo`} />
          </div>

          <div className="rate-details">
            {/* <p>Transfer time: {rateData.timestamp}</p> */}
            {/* <p>Fee & rates: 10</p> */}
            <div className='exchange-rate'>
              <span className="exchange-rate-label">Exchange rate: </span>
              <span className="exchange-rate-value">
                {rateData.rate} {rateData.targetCurrency}
              </span>
            </div>
            <div className='rate-obtained'>
              <span className="rate-obtained-label">
                Rate Obtained {timeAgo(rateData.timestamp)}
              </span>
            </div>
            {/* <p className="text-success">' '</p> */}
          </div>
          <div className="recipient-gets">
            <p>
              {rateData.amount} {rateData.targetCurrency}
            </p>
          </div>
          <a href={rateData.providerName} className="btn btn-primary">
            Go to {rateData.providerName}
          </a>
        </div>
      </div>
    </div>
  )
}

export default ExchangeRateCard

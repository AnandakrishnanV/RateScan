import './ExchangeRateCard.scss'
import { timeAgo } from '../../services/utilService';

const ExchangeRateCard = ({ rateData }) => {
    return (
      <div className="card exchange-rate-card mb-3">
        <div className="card-body">
          <div className="d-flex justify-content-between align-items-center">
            <div className="rate-score">
              <span className="badge bg-success">8</span>
              <span className="provider-name">Wise</span>
            </div>
            <div className="rate-details">
              {/* <p>Transfer time: {rateData.timestamp}</p> */}
              {/* <p>Fee & rates: 10</p> */}
              <p>Exchange rate: {rateData.rate} {rateData.targetCurrency}</p>
              <p>Rate Obtained: {timeAgo(rateData.timestamp)}</p>
              <p className="text-success">{rateData.sourceCurrency}</p>
            </div>
            <div className="recipient-gets">
              <p>{rateData.amount} {rateData.targetCurrency}</p>
            </div>
            <a href={rateData.providerName} className="btn btn-primary">Go to {rateData.providerName}</a>
          </div>
        </div>
      </div>
    );
  };

  export default ExchangeRateCard;
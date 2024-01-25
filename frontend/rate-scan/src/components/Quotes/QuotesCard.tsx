import './QuotesCard.scss'
import providerLogos from '../../services/providerLogos'
import currencySymbols from '../../Common-Currency.json'
import { timeAgo, timeLeft, timeLeftDays } from '../../services/dateUtilService'
import { formatStringToLanguage } from '../../services/stringUtilService'

const QuotesCard = ({ quoteData }) => {
  const logoUrl = providerLogos[quoteData.providerName]

  console.log(logoUrl)

  return (
    <div className="card quote-card mb-3">
      <div className="card-body">
        <div className="d-flex justify-content-between align-items-center">
          <div className="container">
            <div className="row">
              <div className="col col-sm-3 logo-col">
                <div className="provider-logo">
                  <img src={logoUrl} alt={`${quoteData.providerName} logo`} />
                </div>
              </div>
              <div className="col col-sm-6 details-col">
                <div className="row rate-row">
                  <div className="rate-details">
                    <div className="exchange-rate">
                      <span className="exchange-rate-label">
                        Exchange rate:{' '}
                      </span>
                      <span className="exchange-rate-value">
                        {currencySymbols[quoteData.targetCurrency].symbol}
                        {quoteData.rate}
                      </span>
                    </div>
                    <div className="rate-obtained">
                      <span className="rate-obtained-label">
                        Rate Expires {timeLeft(quoteData.expirationTime)}
                      </span>
                    </div>
                    {/* <p className="text-success">' '</p> */}
                  </div>
                </div>
                <div className="row transfer-row">
                  <div className="transfer-details">
                    <span className="transfer-time-label">
                      Estimated to Arrive:{' '}
                    </span>
                    <div>
                      <span className="transfer-time-value">
                        {timeLeftDays(
                          quoteData.paymentOptions[0].estimatedDelivery,
                        )}
                      </span>
                    </div>

                    <div className="payment-method">
                      <span className="payment-method-label">Pay by </span>
                      <span className="payment-method-value">
                        {formatStringToLanguage(
                          quoteData.paymentOptions[0].payIn,
                        )}
                      </span>
                    </div>
                    {/* <p className="text-success">' '</p> */}
                  </div>
                </div>
              </div>
              <div className="col amount-col align-self-center">
                <div className="row">
                  <span className="recipient-gets">
                    {quoteData.paymentOptions[0].targetAmount} {' '}
                    {quoteData.targetCurrency}
                  </span>
                  <div className="fee">
                    <span className="fee-label">Fees: </span>
                    <span className="fee-value">
                      {quoteData.paymentOptions[0].fee} {' '}
                      {quoteData.sourceCurrency}
                    </span>
                  </div>
                </div>
                <div className="row ">
                  <div className="provider-button">
                    <a
                      href={quoteData.providerName}
                      className="btn btn-primary"
                      role="button"
                    >
                      Go to {quoteData.providerName}
                    </a>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

export default QuotesCard

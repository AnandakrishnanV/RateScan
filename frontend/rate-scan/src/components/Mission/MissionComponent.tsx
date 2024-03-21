import './MissionComponent.scss'
import currencyGlobe from '../../assets/currency.png'

const MissionComponent = () => {
  return (
    <div className="mission-container">
      <div className="container">
        <div className="card why-us-card mx-auto">
          <div className="row g-0">
            <div className="col-md-6">
              <div className="card-body">
                <h5 className="card-title font-weight-bold">
                  Empowering Your Currency Choices
                </h5>
                <p className="card-text">
                  In a world where every penny counts, we believe in financial
                  empowerment through informed decisions. Our platform offers
                  comprehensive, up-to-date comparisons of Forex rates from
                  various providers and banks, ensuring you don't lose out on
                  your hard-earned money to excessive fees and poor exchange
                  rates. Whether it's paying for tuition or supporting families
                  back home, our mission is to help you save more.
                </p>
              </div>
            </div>
            <div className="col-md-6">
              <img
                src={currencyGlobe}
                className="img-fluid rounded-start"
                alt="Descriptive Image Alt Text"
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

export default MissionComponent

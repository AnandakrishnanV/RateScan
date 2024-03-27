import './HowItWorksComponent.scss'
import hiw1 from '../../assets/hiw1.png'
import hiw2 from '../../assets/hiw2.png'
import hiw3 from '../../assets/hiw3.png'

const HowItWorksComponent = () => {
  const steps = [
    {
      img: hiw1,
      title: 'Select Your Currencies',
      text:
        'Choose the currencies you wish to compare and see real-time rates.',
    },
    {
      img: hiw2,
      title: 'Compare Providers',
      text:
        'View a list of money-transfer services and banks, showcasing their rates and fees transparently',
    },
    {
      img: hiw3,
      title: 'Make an Informed Decision',
      text:
        'Use our comprehensive data to choose the best option for your needs, saving you money and time',
    },
  ]

  return (
    <div id="how-it-works" className="how-it-works-container">
      <div className="container">
        <h2 className="text-center">How It Works</h2>
        <div className="row">
          {steps.map((step, index) => (
            <div key={index} className="col-flex">
              <img
                src={step.img}
                className="img-fluid mb-3"
                alt={`Step ${index + 1}`}
              />
              <div className="step-title">
                <p>{step.title}</p>
              </div>
              <div className='step-text'>
                <p>{step.text}</p>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  )
}

export default HowItWorksComponent

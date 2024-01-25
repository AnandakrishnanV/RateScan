import { Controller, useForm } from 'react-hook-form'
import { useNavigate } from 'react-router-dom'
import Select, { ControlProps, components } from 'react-select'
import currencyOpts from '../../currency.json'
import {
  fetchExchangeRateWithAmount,
  fetchQuotes,
} from '../../services/apiService'
import './CurrencyComponent.scss'

const CurrencyComponent = () => {
  const { control, handleSubmit, register } = useForm()
  const navigate = useNavigate()

  const onSubmit = async (data: any) => {
    console.log(data)

    try {
      const result = await fetchExchangeRateWithAmount(
        data.sourceCurrency.value,
        data.targetCurrency.value,
        data.amount,
      )
      console.log(result)
      navigate('/exchange-rates', { state: { exchangeRates: result } })
    } catch (error) {
      console.error('API call failed:', error)
    }
  }

  const onQuote = async (data: any) => {
    console.log('Quote', data)

    try {
      const result = await fetchQuotes(
        data.sourceCurrency.value,
        data.targetCurrency.value,
        data.amount,
      )
      console.log(result)

      navigate('/quotes', { state: { quotes: result } })
    } catch (error) {
      console.error('API call failed:', error)
    }
  }

  const customOption = (props: any) => {
    return (
      <components.Option {...props}>
        <img
          src={props.data.icon}
          alt={props.data.label}
          style={{ width: '20px', height: '20px', marginRight: '10px' }}
        />
        {props.data.label}
      </components.Option>
    )
  }

  const customControl: React.FC<ControlProps<any, false>> = (props) => {
    // what even is this?
    // Check if there is a selected option
    const hasValue = !!props.getValue().length
    const value = hasValue ? props.getValue()[0] : null // Get the first selected option

    return (
      <components.Control {...props}>
        {hasValue && value.icon && (
          <img
            src={value.icon}
            alt={value.label}
            style={{ width: '20px', height: '20px', marginLeft: '6px' }}
          />
        )}
        {props.children}
      </components.Control>
    )
  }

  const customStyles = {}

  return (
    <form className="currency-converter-form" onSubmit={handleSubmit(onQuote)}>
      <div className="row mb-3">
        <div className="col select-container">
          <Controller
            name="sourceCurrency"
            control={control}
            render={({ field }) => (
              <Select
                {...field}
                options={currencyOpts}
                components={{
                  Option: customOption,
                  Control: customControl,
                }}
                styles={customStyles}
                placeholder="Select Source Currency"
              />
            )}
          />
        </div>
        <div className="col select-container">
          <Controller
            name="targetCurrency"
            control={control}
            render={({ field }) => (
              <Select
                {...field}
                options={currencyOpts}
                components={{
                  Option: customOption,
                  Control: customControl,
                }}
                styles={customStyles}
                placeholder="Select Target Currency"
              />
            )}
          />
        </div>
      </div>

      <div className="mb-3">
        <label htmlFor="amount" className="form-label">
          Amount:
        </label>
        <input
          type="number"
          className="form-control"
          id="amount"
          {...register('amount', { required: true })}
          placeholder="Enter amount"
        />
      </div>
      <div className="button-container">
        <button type="submit" className="btn btn-primary">
          Get Quote
        </button>
      </div>
      {/* <button type="button" onClick={onSubmit} className="btn btn-secondary">
      Convert
      </button> */}
    </form>
  )
}

export default CurrencyComponent

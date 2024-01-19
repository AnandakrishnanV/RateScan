import { Controller, useForm } from 'react-hook-form'
import Select from 'react-select'
import { fetchExchangeRateWithAmount } from '../../services/apiService'
import './CurrencyComponent.scss'
import { useNavigate } from 'react-router-dom'

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
      var catResult = result.concat(result)
      catResult = catResult.concat(catResult)
      navigate('/exchange-rates', {state: {exchangeRates: catResult}})
    } catch (error) {
      console.error('API call failed:', error)
    }
  }

  const customStyles = {
    // Custom styles for react-select
    // Refer to https://react-select.com/styles for styling options
  }

  const currencyOptions = [
    {
      value: 'USD',
      label: 'USD - United States Dollar',
      icon: 'path-to-us-flag',
    },
    { value: 'EUR', label: 'EUR - Euro', icon: 'path-to-eu-flag' },
    // ... more currency options
  ]

  return (
    <form className="currency-converter-form" onSubmit={handleSubmit(onSubmit)}>
      <div className="row mb-3">
        <div className="col select-container">
          <Controller
            name="sourceCurrency"
            control={control}
            render={({ field }) => (
              <Select
                {...field}
                options={currencyOptions}
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
                options={currencyOptions}
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

      <button type="submit" className="btn btn-primary">
        Convert
      </button>
    </form>
  )
}

export default CurrencyComponent

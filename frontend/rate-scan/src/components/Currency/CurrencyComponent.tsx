import { Controller, useForm } from 'react-hook-form'
import { useNavigate } from 'react-router-dom'
import Select, { ControlProps, components } from 'react-select'
import currencyOpts from '../../currency.json'
import {
  // fetchExchangeRateWithAmount,
  // fetchQuotes ,
   fetchQuotesWithCountry,
} from '../../services/apiService'
import './CurrencyComponent.scss'
import { useState } from 'react'

const CurrencyComponent = () => {
  const { control, handleSubmit, register } = useForm()
  const navigate = useNavigate()

  const [isHoveredAmount, setIsHoveredAmount] = useState(false)

  // const onSubmit = async (data: any) => {
  //   console.log(data)

  //   try {
  //     const result = await fetchExchangeRateWithAmount(
  //       data.sourceCurrency.value,
  //       data.targetCurrency.value,
  //       data.amount,
  //     )
  //     console.log(result)
  //     navigate('/exchange-rates', { state: { exchangeRates: result } })
  //   } catch (error) {
  //     console.error('API call failed:', error)
  //   }
  // }

  const onQuote = async (data: any) => {
    console.log('Quote', data)

    try {
      const result = await fetchQuotesWithCountry(
        data.sourceCurrency.value,
        data.targetCurrency.value,
        data.amount,
        data.sourceCurrency.countryCode,
        data.targetCurrency.countryCode
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
            style={{ width: '20px', height: '20px', marginLeft: '30px' }}
          />
        )}
        {props.children}
      </components.Control>
    )
  }

  const customStyles = {
    control: (provided: any, state: any) => ({
      ...provided,
      height: '60px',
      width: '320px',
      fontFamily: 'Raleway',
      fontWeight: 'bold',
      fontSize: '18px',
      borderColor: '#b7d4a2',
      borderWidth: '2px',
      borderStyle: 'solid',
      boxShadow: state.isFocused ? 0 : 0,
      '&:hover': {
        borderColor: '#99c877',
      },
    }),
    menu: (provided: any) => ({
      ...provided,
      backgroundColor: 'white',
    }),
    option: (provided: any, state: any) => ({
      ...provided,
      height: 'auto',
      padding: '15px',
      fontFamily: 'Raleway',
      fontWeight: 'bold',
      fontSize: '18px',
      color: 'black',
      backgroundColor: state.isFocused ? '#d8efc8' : 'white',
      borderBottom: '1px solid #d8efc8',
      '&:hover, &:focus': {
        backgroundColor: '#d8efc8',
        borderRadius: '4px',
      },
    }),
    menuList: (provided: any) => ({
      ...provided,
      padding: '1rem',
    }),
    container: (provided: any) => ({
      ...provided,
    }),
  }

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
        <div className='amount-and-button-container'>
          <div className="amount-container">
            <input
              type="number"
              className="form-control amount-input"
              id="amount"
              {...register('amount', { required: true })}
              placeholder="Enter amount"
              style={{
                height: '80px',
                width: '400px',
                borderWidth: '2px',
                borderStyle: 'solid',
                fontSize: '22px',
                fontWeight: '700',
                fontFamily: 'Cabin, sans-serif',
                borderColor: isHoveredAmount ? '#99c877' : '#b7d4a2',
                boxShadow: 'none',
                outline: 'none',
              }}
              onMouseEnter={() => setIsHoveredAmount(true)}
              onMouseLeave={() => setIsHoveredAmount(false)}
            />
          </div>
          <div className="button-container">
            <button type="submit" className="btn btn-primary">
              Compare
            </button>
          </div>
        </div>
      </div>

      {/* <button type="button" onClick={onSubmit} className="btn btn-secondary">
      Convert
      </button> */}
    </form>
  )
}

export default CurrencyComponent

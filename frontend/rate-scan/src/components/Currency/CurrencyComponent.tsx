import { Controller, useForm } from 'react-hook-form'
import { useNavigate } from 'react-router-dom'
import Select, { ControlProps, components } from 'react-select'
import currencyOpts from '../../currency.json'
import { fetchExchangeRateWithAmount } from '../../services/apiService'
import './CurrencyComponent.scss'

const CurrencyComponent = () => {
  const { control, handleSubmit, register } = useForm()
  const navigate = useNavigate()

  const onSubmit = async (data: any) => {
    console.log(data)
    console.log(currencyOpts)

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
    );
  };

  const customControl: React.FC<ControlProps<any, false>> = (props) => {            // what even is this?
    // Check if there is a selected option
    const hasValue = !!props.getValue().length;
    const value = hasValue ? props.getValue()[0] : null; // Get the first selected option
  
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
    );
  };

  const customStyles = {
  }

  const currencyOptions = [
  //   {
  //     "code": "INR",
  //     "name": "Indian Rupee",
  //     "country": "India",
  //     "countryCode": "IN",
  //     "flag": "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAAACXBIWXMAAAsTAAALEwEAmpwYAAAAIGNIUk0AAHolAACAgwAA+f8AAIDpAAB1MAAA6mAAADqYAAAXb5JfxUYAAAJDSURBVHja7JfNaxNBGIef2WwalaahhaaYUm1ta4tivViUHqxSRISeBG/SP0vwVPDkTfAiqIh4ED8OORRrFT8qghZrpYkxu9mdmddDYhtFwak4ufQHy+zC7Mwz837MO0pE6KQCOqxdAAVkgFyr9SkDNEKgp7J4+YsEfudXKqCwsNgXAgUJFNlDM36X/+klQCEEclgLOkHiKiBt1qHtu91q8pv3X/vwx35qTw+iGwC5EABrER0hOvazfB2DNQC0ADSkcfPxoUwWbPozgCR1JI08BX8GTBuAWIM0akhS9+eFOtnyjgkRWXH9vx5r3n+oYrAMFvMUunM7CEU1Ge4E/tmrz9x7tMrxyQEA7j95x5HRImemh/5/Ko6TlBt3XnDp/CTfooRKrcHFuQnKz9f4uF7bUSp2MkF5eY2NzYgktdx9vEqlGnNuZoSxA72srdeYPzvuZALnHWikBhGIE009SqnVU+qxBiBqtc4mcClKjo73c/vhW05OlZg9McSF06PMnRrm1oM3TE+V/nqcH3M6A+T3dTE/O8aV62X29+cZKRW4dnOJsYO9DA8WnAEUMJGm6UoYugXExmbE8usNjLEcHu6jVOx2SwNak81mm2E4fnUByQQkrezkrKdu3bsyWYLmUdDMhNoYwjBA8FOgKgXa6m0Aay2Imy/8kwSs0dtOaI1BKZ/VEFjTHgVWUPgjUKjmrm+dhghKKbq79nqDsLINYESE6malE1W5UcAAcAzo9zz5OrCkWneCfKv1qQbwVe1eTjsN8H0AbQf7MRxAQMIAAAAASUVORK5CYII="
  // }
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
                options={currencyOpts}
                components={{ 
                  Option: customOption,
                  Control: customControl 
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
                  Control: customControl 
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

      <button type="submit" className="btn btn-primary">
        Convert
      </button>
    </form>
  )
}

export default CurrencyComponent

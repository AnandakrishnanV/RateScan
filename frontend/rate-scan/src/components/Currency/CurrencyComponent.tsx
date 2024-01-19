import { Controller, useForm } from 'react-hook-form'
import Select from 'react-select'
import './CurrencyComponent.scss'

const CurrencyComponent = () => {
  const { control, handleSubmit, register } = useForm()

  const onSubmit = (data: any) => {
    console.log(data)
    // Add your form submission logic here
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
        <label htmlFor="amount" className="form-label">Amount:</label>
        <input 
          type="number" 
          className="form-control" 
          id="amount" 
          {...register("amount", { required: true })} 
          placeholder="Enter amount"
        />
      </div>

      <button type="submit" className="btn btn-primary">Convert</button>
    </form>
  );
};

export default CurrencyComponent

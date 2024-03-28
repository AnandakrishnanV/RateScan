import axios from 'axios'

export const apiClient = axios.create({
  //  baseURL: 'http://localhost:5000',
   baseURL: 'https://api.ratescan.net/',
})

export const fetchExchangeRate = async (
  sourceCurrency: String,
  targetCurrency: string,
) => {
  try {
    const response = await apiClient.get('/api/v1/rates', {
      params: {
        sourceCurrency,
        targetCurrency,
      },
    })
    return response.data
  } catch (error) {
    console.error()
    throw error
  }
}

export const fetchExchangeRateWithAmount = async (
  sourceCurrency: String,
  targetCurrency: string,
  amount: number,
) => {
  try {
    const response = await apiClient.get('/api/v1/rates/convert', {
      params: {
        sourceCurrency,
        targetCurrency,
        amount,
      },
    })
    return response.data
  } catch (error) {
    console.error()
    throw error
  }
}

export const fetchQuotes = async (
    sourceCurrency: String,
    targetCurrency: string,
    sourceAmount: number,
    //targetAmount: number,
  ) => {
    try {
      const response = await apiClient.get('/api/v1/rates/quotes', {
        params: {
          sourceCurrency,
          targetCurrency,
          sourceAmount,
          // targetAmount,
        },
      })
      return response.data
    } catch (error) {
      console.error()
      throw error
    }
  }

  export const fetchQuotesWithCountry = async (
    sourceCurrency: string,
    targetCurrency: string,
    sourceAmount: number,
    sourceCountry: string,
    targetCountry: string
  ) => {
    try {
      const response = await apiClient.get('/api/v1/rates/quotes', {
        params: {
          sourceCurrency,
          targetCurrency,
          sourceAmount,
          sourceCountry,
          targetCountry,
        },
      })
      return response.data
    } catch (error) {
      console.error()
      throw error
    }
  }

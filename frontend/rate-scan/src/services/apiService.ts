import axios from 'axios'

export const apiClient = axios.create({
  baseURL: 'http://localhost:8080',
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

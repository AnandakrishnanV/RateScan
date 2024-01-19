import axios from "axios"

export const apiClient = axios.create({
    baseURL: "http://localhost:8080",
})

export const fetchExchangeRate =async (sourceCurrency:String, targetCurrency: string, amount: number) => {
   try {
    const response = await apiClient.get('/api/v1/rates', {
        params: {
            sourceCurrency,
            targetCurrency
        }
    })
    return response.data
   }
   catch (error) {
    console.error();
    throw error;
   }
}
package com.ak.ratecompare.exchangerate.apiclients;

import com.ak.ratecompare.exchangerate.model.exchangeRateQuote.ExchangeRateQuote;

public interface ExchangeRateQuoteApiClient {

	String getProviderName();

	ExchangeRateQuote fetchRate(String sourceCurrency, String targetCurrency, Double sourceAmount, Double targetAmount, String sourceCountry, String targetCountry);
	
	

}

package com.ak.ratecompare.exchangerate.apiclients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ak.ratecompare.exchangerate.apihandlers.FetchWiseQuoteFromApi;
import com.ak.ratecompare.exchangerate.model.exchangeRateQuote.ExchangeRateQuote;
import com.ak.ratecompare.exchangerate.repository.ExchangeRateQuoteRepository;

@Component
public class WiseQuoteApiClient implements ExchangeRateQuoteApiClient {
	@Autowired
	private ExchangeRateQuoteRepository exchangeRateQuoteRepository;
	
	@Autowired
	private FetchWiseQuoteFromApi fetchWiseQuoteFromApi;
	
	@Override
	public String getProviderName() {
		return "Wise";
	}

	@Override
	public ExchangeRateQuote fetchRate(String sourceCurrency, String targetCurrency, Double sourceAmout,
			Double targetAmount) {
		
		ExchangeRateQuote exchangeRateQuote = fetchWiseQuoteFromApi.fetchQuote(sourceCurrency, targetCurrency, sourceAmout, targetAmount);

		return exchangeRateQuoteRepository.save(exchangeRateQuote);

	}
}

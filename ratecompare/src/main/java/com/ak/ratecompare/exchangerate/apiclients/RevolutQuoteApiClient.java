package com.ak.ratecompare.exchangerate.apiclients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ak.ratecompare.exchangerate.apihandlers.FetchRevolutQuoteFromApi;
import com.ak.ratecompare.exchangerate.model.exchangeRateQuote.ExchangeRateQuote;
import com.ak.ratecompare.exchangerate.repository.ExchangeRateQuoteRepository;

// Still Using the public API for now, will change if/when usage hits

@Component
public class RevolutQuoteApiClient implements ExchangeRateQuoteApiClient {

	@Autowired
	private ExchangeRateQuoteRepository exchangeRateQuoteRepository;

	@Autowired
	private FetchRevolutQuoteFromApi fetchRevolutQuoteFromApi;

	@Override
	public String getProviderName() {
		return "Revolut";
	}

	@Override
	public ExchangeRateQuote fetchRate(String sourceCurrency, String targetCurrency, Double sourceAmout,
			Double targetAmount, String sourceCountry, String targetCountry) {

		ExchangeRateQuote exchangeRateQuote = fetchRevolutQuoteFromApi.fetchQuote(sourceCurrency, targetCurrency,
				sourceAmout, targetAmount);

		return exchangeRateQuoteRepository.save(exchangeRateQuote);

	}

}

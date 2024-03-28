package com.ak.ratecompare.exchangerate.apiclients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ak.ratecompare.exchangerate.apihandlers.remitly.FetchRemitlyQuoteFromApi;
import com.ak.ratecompare.exchangerate.apihandlers.wise.FetchWiseQuoteFromApi;
import com.ak.ratecompare.exchangerate.model.exchangeRateQuote.ExchangeRateQuote;
import com.ak.ratecompare.exchangerate.repository.ExchangeRateQuoteRepository;

@Component
public class RemitlyQuoteApiClient implements ExchangeRateQuoteApiClient {
	@Autowired
	private ExchangeRateQuoteRepository exchangeRateQuoteRepository;

	@Autowired
	private FetchRemitlyQuoteFromApi fetchRemitlyQuoteFromApi;

	@Override
	public String getProviderName() {
		return "Remitly";
	}

	@Override
	public ExchangeRateQuote fetchRate(String sourceCurrency, String targetCurrency, Double sourceAmout,
			Double targetAmount, String sourceCountry, String targetCountry) {

		ExchangeRateQuote exchangeRateQuote = fetchRemitlyQuoteFromApi.fetchQuote(sourceCurrency, targetCurrency,
				sourceAmout, targetAmount, sourceCountry, targetCountry);

		return exchangeRateQuoteRepository.save(exchangeRateQuote);

	}
}

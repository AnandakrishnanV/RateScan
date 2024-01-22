package com.ak.ratecompare.exchangerate.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ak.ratecompare.exchangerate.model.ExchangeRate;
import com.ak.ratecompare.exchangerate.model.exchangeRateQuote.ExchangeRateQuote;

@Service
public class ExchangeRateQuoteAggregatorService {

	@Autowired
	private ExchangeRateQuoteService exchangeRateQuoteService;
	
	@Autowired
	private ExchangeRateClientManagerService clientManagerService;
	
	public List<ExchangeRateQuote> getExchangeRateFromAllProviders(String sourceCurrency, String targetCurrency, Double sourceAmount, Double targetAmount) {
		
		return clientManagerService.getAllQuoteClients().stream()
				.map(client -> exchangeRateQuoteService.getQuotes(sourceCurrency, targetCurrency, sourceAmount, targetAmount, client.getProviderName()))
				.collect(Collectors.toList());
	}
	
}

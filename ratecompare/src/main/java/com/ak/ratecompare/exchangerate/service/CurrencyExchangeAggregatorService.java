package com.ak.ratecompare.exchangerate.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ak.ratecompare.exchangerate.model.ExchangeRate;

@Service
public class CurrencyExchangeAggregatorService {

	@Autowired
	private CurrencyExchangeService currencyExchangeService;
	
	@Autowired
	private ExchangeRateClientManagerService clientManagerService;
	
	public List<ExchangeRate> getExchangeRateFromAllProviders(String sourceCurrency, String targetCurrency) {
		
		return clientManagerService.getAllClients().stream()
				.map(client -> currencyExchangeService.getExchangeRate(sourceCurrency, targetCurrency, client.getProviderName()))
				.collect(Collectors.toList());
	}
	
}

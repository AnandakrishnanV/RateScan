package com.ak.ratecompare.exchangerate.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ak.ratecompare.exchangerate.interfaces.ExchangeRateApiClient;
import com.ak.ratecompare.exchangerate.model.ExchangeRate;
import com.ak.ratecompare.exchangerate.repository.ExchangeRateRepository;

@Service
public class CurrencyExchangeService {
	
	@Autowired
	private ExchangeRateRepository exchangeRateRepository;
	
	@Value("${app.cacheDurationInMinutes}")
    private int cacheDurationInMinutes;
	
	@Autowired
    private ExchangeRateClientManagerService clientManager;
																										
	public ExchangeRate getExchangeRate(String sourceCurrency, String targetCurrency, String provider) {
		
		Optional<ExchangeRate> cachedRate = exchangeRateRepository.findBySourceCurrencyAndTargetCurrencyAndProviderOrderByTimestampDesc(sourceCurrency, targetCurrency, provider);
		
		if(cachedRate.isPresent() && !cachedRate.get().isStale(cacheDurationInMinutes)) {
			return cachedRate.get();
		}
		else {
			ExchangeRateApiClient client = clientManager.findClientByName(provider);
			if (client != null) {
	            // Fetch and save new rate
				ExchangeRate newRate = client.fetchRate(sourceCurrency, targetCurrency);
				exchangeRateRepository.save(newRate);
	        }
			// add exception handling
			return null;
		}
	}

}

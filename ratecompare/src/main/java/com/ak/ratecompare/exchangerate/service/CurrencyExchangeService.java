package com.ak.ratecompare.exchangerate.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ak.ratecompare.exchangerate.apiclients.ExchangeRateApiClient;
import com.ak.ratecompare.exchangerate.model.ExchangeRate;
import com.ak.ratecompare.exchangerate.model.Provider;
import com.ak.ratecompare.exchangerate.repository.ExchangeRateRepository;
import com.ak.ratecompare.exchangerate.repository.ProviderRepository;

@Service
public class CurrencyExchangeService {
	
	@Autowired
	private ExchangeRateRepository exchangeRateRepository;
	
	@Value("${app.cacheDurationInMinutes}")
    private String cacheDurationInMinutes;
	
	@Autowired
    private ExchangeRateClientManagerService clientManager;
	
	@Autowired
    private ProviderRepository providerRepository;
																										
	public ExchangeRate getExchangeRate(String sourceCurrency, String targetCurrency, String providerName) {
		
		Provider provider = providerRepository.findById(providerName).orElseThrow(() -> new RuntimeException("Provider not found"));
        
        Optional<ExchangeRate> cachedRate = exchangeRateRepository
            .findTopBySourceCurrencyAndTargetCurrencyAndProviderNameOrderByTimestampDesc(sourceCurrency, targetCurrency, provider.getName());
		
		if(cachedRate.isPresent() && !cachedRate.get().isStale(Integer.parseInt(cacheDurationInMinutes))) {
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

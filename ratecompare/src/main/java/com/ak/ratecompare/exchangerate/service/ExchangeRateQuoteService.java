package com.ak.ratecompare.exchangerate.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ak.ratecompare.exchangerate.apiclients.ExchangeRateQuoteApiClient;
import com.ak.ratecompare.exchangerate.model.ExchangeRate;
import com.ak.ratecompare.exchangerate.model.Provider;
import com.ak.ratecompare.exchangerate.model.exchangeRateQuote.ExchangeRateQuote;
import com.ak.ratecompare.exchangerate.repository.ExchangeRateQuoteRepository;
import com.ak.ratecompare.exchangerate.repository.ExchangeRateRepository;
import com.ak.ratecompare.exchangerate.repository.ProviderRepository;

@Service
public class ExchangeRateQuoteService {
	
	@Autowired
	private ExchangeRateRepository exchangeRateRepository;
	
	@Autowired
	private ExchangeRateQuoteRepository exchangeRateQuoteRepository;
	
	@Value("${app.cacheDurationInMinutes}")
    private String cacheDurationInMinutes;
	
	@Autowired
    private ExchangeRateClientManagerService clientManager;
	
	@Autowired
    private ProviderRepository providerRepository;
																										
	public ExchangeRateQuote getQuotes(String sourceCurrency, String targetCurrency, Double sourceAmount, Double targetAmount, String providerName, String sourceCountry, String targetCountry) {
		
		System.out.println(providerName);
		
		Provider provider = providerRepository.findById(providerName).orElseThrow(() -> new RuntimeException("Provider not found"));
        
        Optional<ExchangeRate> cachedRate = exchangeRateRepository
            .findTopBySourceCurrencyAndTargetCurrencyAndProviderNameOrderByTimestampDesc(sourceCurrency, targetCurrency, provider.getName());
        														// Integer.parseInt(cacheDurationInMinutes)
        if(cachedRate.isPresent() && !cachedRate.get().isStale(0)) {
			return null;
        	//return cachedRate.get();
		}
		else {

			ExchangeRateQuoteApiClient client = clientManager.findQuoteClientByName(provider); // might need change
			if (client != null) {
	            // Fetch and save new rate
				return client.fetchRate(sourceCurrency, targetCurrency, sourceAmount, targetAmount, sourceCountry, targetCountry);
				 
	        }
			 // Handle the case where the client is not found
		    throw new RuntimeException("Exchange rate provider client not found.");
		}
	}

}

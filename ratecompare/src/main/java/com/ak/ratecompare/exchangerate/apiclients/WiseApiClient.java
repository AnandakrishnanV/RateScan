package com.ak.ratecompare.exchangerate.apiclients;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ak.ratecompare.exchangerate.model.ExchangeRate;
import com.ak.ratecompare.exchangerate.model.Provider;
import com.ak.ratecompare.exchangerate.repository.ProviderRepository;

@Component
public class WiseApiClient implements ExchangeRateApiClient{
	
	@Autowired
    private ProviderRepository providerRepository;

	@Override
	public String getProviderName() {
		return "Wise";
	}

	@Override
	public ExchangeRate fetchRate(String sourceCurrency, String targetCurrency) {
		// Fetch the Wise provider from the database
        Provider wiseProvider = providerRepository.findById(getProviderName())
            .orElseThrow(() -> new RuntimeException("Provider not found"));

        // Dummy implementation - returns a predefined exchange rate
        return new ExchangeRate(sourceCurrency, targetCurrency, wiseProvider, new BigDecimal("0.85"), LocalDateTime.now());
	}
}

package com.ak.ratecompare.exchangerate.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ak.ratecompare.exchangerate.model.midMarket.CurrencyExchangeRate;
import com.ak.ratecompare.exchangerate.repository.CurrencyExchangeRateRepository;

@Service
public class PopularPairService {

	@Autowired
    private CurrencyExchangeRateRepository currencyExchangeRateRepository;

	public List<CurrencyExchangeRate> findLatestRateForPairs(List<String> currencies) {
		List<CurrencyExchangeRate> latestRates = new ArrayList<>();
		currencies.forEach(baseCurrency -> {
			List<String> targets = currencies.stream().filter(target -> !target.equals(baseCurrency)).collect(Collectors.toList());
			
			targets.forEach(targetCurrency -> {
				CurrencyExchangeRate latestRate = findLatestRateForPair(baseCurrency, targetCurrency);
				if(latestRate != null) {
					latestRates.add(latestRate);
				}
			});
		});
		return latestRates;
		
	}
	
    public CurrencyExchangeRate findLatestRateForPair(String baseCurrencyCode, String targetCurrencyCode) {
        return currencyExchangeRateRepository.findTop1ByBaseCurrencyCurrencyCodeAndTargetCurrencyCurrencyCodeOrderByRateDateDesc(baseCurrencyCode, targetCurrencyCode);
    }

}

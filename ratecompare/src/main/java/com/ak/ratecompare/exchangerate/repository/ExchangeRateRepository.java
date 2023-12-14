package com.ak.ratecompare.exchangerate.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ak.ratecompare.exchangerate.model.ExchangeRate;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

	// Find the latest exchange rate for a specific currency pair from all providers
    List<ExchangeRate> findBySourceCurrencyAndTargetCurrencyOrderByTimestampDesc(String sourceCurrency, String targetCurrency);
    
    // Find the latest exchange rate for a specific currency pair from a specific provider
    Optional<ExchangeRate> findBySourceCurrencyAndTargetCurrencyAndProviderOrderByTimestampDesc(String sourceCurrency, String targetCurrency, String Provider);
}

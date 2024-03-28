package com.ak.ratecompare.exchangerate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ak.ratecompare.exchangerate.model.ExchangeRate;
import com.ak.ratecompare.exchangerate.model.midMarket.CurrencyExchangeRate;

@Repository
public interface CurrencyExchangeRateRepository extends JpaRepository<CurrencyExchangeRate, Long> {

	CurrencyExchangeRate findTop1ByBaseCurrencyCurrencyCodeAndTargetCurrencyCurrencyCodeOrderByRateDateDesc(String baseCurrencyCode, String targetCurrencyCode);
}

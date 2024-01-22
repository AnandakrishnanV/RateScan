package com.ak.ratecompare.exchangerate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ak.ratecompare.exchangerate.model.ExchangeRate;
import com.ak.ratecompare.exchangerate.model.exchangeRateQuote.ExchangeRateQuote;

public interface ExchangeRateQuoteRepository extends JpaRepository<ExchangeRate, Long> {

	ExchangeRateQuote save(ExchangeRateQuote exchangeRateQuote);

}

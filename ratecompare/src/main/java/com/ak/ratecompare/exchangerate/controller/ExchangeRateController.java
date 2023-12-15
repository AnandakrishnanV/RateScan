package com.ak.ratecompare.exchangerate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ak.ratecompare.exchangerate.model.ExchangeRate;
import com.ak.ratecompare.exchangerate.service.CurrencyExchangeAggregatorService;

@RestController
@RequestMapping("api/v1/exchange-rates")
public class ExchangeRateController {

	@Autowired
	private CurrencyExchangeAggregatorService aggregatorService;

	public ResponseEntity<List<ExchangeRate>> getExchangeRates(@RequestParam String sourceCurrency,
			@RequestParam String targetCurrency) {

		List<ExchangeRate> exchangeRates = aggregatorService.getExchangeRateFromAllProviders(sourceCurrency,
				targetCurrency);
		return ResponseEntity.ok(exchangeRates);
	}

}

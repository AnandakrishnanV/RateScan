package com.ak.ratecompare.exchangerate.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ak.ratecompare.exchangerate.model.ExchangeRate;
import com.ak.ratecompare.exchangerate.model.ExchangeRateAmountDTO;
import com.ak.ratecompare.exchangerate.model.ExchangeRateDTO;
import com.ak.ratecompare.exchangerate.service.CurrencyExchangeAggregatorService;
import com.ak.ratecompare.exchangerate.util.ExchangeRateMapper;

@RestController
@RequestMapping("api/v1/rates")
@CrossOrigin(origins = "http://localhost:5173")
public class ExchangeRateController {

	@Autowired
	private CurrencyExchangeAggregatorService aggregatorService;

	@GetMapping
	public ResponseEntity<List<ExchangeRateDTO>> getExchangeRates(@RequestParam String sourceCurrency,
			@RequestParam String targetCurrency) {

		List<ExchangeRate> exchangeRates = aggregatorService.getExchangeRateFromAllProviders(sourceCurrency,
				targetCurrency);
		
		List<ExchangeRateDTO> dtoList = exchangeRates.stream()
				.map((exchangeRate) -> ExchangeRateMapper.toDTO(exchangeRate))
				.collect(Collectors.toList());
		
		return ResponseEntity.ok(dtoList);
	}
	
	@GetMapping("/convert")
	public ResponseEntity<List<ExchangeRateAmountDTO>> getExchangeRatesWithAmount(@RequestParam String sourceCurrency,
			@RequestParam String targetCurrency, @RequestParam int amount) {

		List<ExchangeRate> exchangeRates = aggregatorService.getExchangeRateFromAllProviders(sourceCurrency,
				targetCurrency);
		
		List<ExchangeRateAmountDTO> dtoList = exchangeRates.stream()
				.map((exchangeRate) -> ExchangeRateMapper.toAmountDTO(exchangeRate, amount))
				.collect(Collectors.toList());
		
		return ResponseEntity.ok(dtoList);
	}
	
//	@GetMapping --> Next Step
//	public ResponseEntity<List<ExchangeRateDTO>> getExchangeQuotes(@RequestParam String sourceCurrency,
//			@RequestParam String targetCurrency, @RequestParam double sourceAmount) {
//
//		List<ExchangeRate> exchangeRates = aggregatorService.getExchangeRateFromAllProviders(sourceCurrency,
//				targetCurrency);
//		
//		List<ExchangeRateDTO> dtoList = exchangeRates.stream()
//				.map((exchangeRate) -> ExchangeRateMapper.toDTO(exchangeRate))
//				.collect(Collectors.toList());
//		
//		return ResponseEntity.ok(dtoList);
//	}

}

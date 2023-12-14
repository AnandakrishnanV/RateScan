package com.ak.ratecompare.exchangerate.dataloader;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ak.ratecompare.exchangerate.model.ExchangeRate;
import com.ak.ratecompare.exchangerate.repository.ExchangeRateRepository;

@Component
public class ExchangeRateDataLoader implements CommandLineRunner {
	
	private final ExchangeRateRepository exchangeRateRepository;

	public ExchangeRateDataLoader(ExchangeRateRepository exchangeRateRepository) {
		this.exchangeRateRepository = exchangeRateRepository;
	}
	
	@Override
	public void run(String... args) throws Exception {
		ExchangeRate rate1 = new ExchangeRate( "USD", "EUR", "Wise", new BigDecimal("0.85"), LocalDateTime.now());
        ExchangeRate rate2 = new ExchangeRate("USD", "EUR", "Revolut", new BigDecimal("0.84"), LocalDateTime.now());
        

        exchangeRateRepository.save(rate1);
        exchangeRateRepository.save(rate2);
	}
	
	

}

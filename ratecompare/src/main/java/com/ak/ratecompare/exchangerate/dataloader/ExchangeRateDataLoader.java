package com.ak.ratecompare.exchangerate.dataloader;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.ak.ratecompare.exchangerate.model.ExchangeRate;
import com.ak.ratecompare.exchangerate.model.Provider;
import com.ak.ratecompare.exchangerate.repository.ExchangeRateRepository;
import com.ak.ratecompare.exchangerate.repository.ProviderRepository;

@Component
@Order(2)
public class ExchangeRateDataLoader implements CommandLineRunner {

	private final ExchangeRateRepository exchangeRateRepository;
	private final ProviderRepository providerRepository;

	public ExchangeRateDataLoader(ExchangeRateRepository exchangeRateRepository,
			ProviderRepository providerRepository) {
		this.exchangeRateRepository = exchangeRateRepository;
		this.providerRepository = providerRepository;
	}

	@Override
	public void run(String... args) throws Exception {

		Provider wiseProvider = providerRepository.findById("Wise")
				.orElseThrow(() -> new RuntimeException("Provider not Found!"));
		Provider revolutProvider = providerRepository.findById("Revolut")
				.orElseThrow(() -> new RuntimeException("Provider not Found!"));

		ExchangeRate rate1 = new ExchangeRate("USD", "EUR", wiseProvider, new BigDecimal("0.45"), LocalDateTime.now().minusDays(1));
		ExchangeRate rate2 = new ExchangeRate("USD", "EUR", revolutProvider, new BigDecimal("0.44"),
				LocalDateTime.now().minusDays(1));

		exchangeRateRepository.save(rate1);
		exchangeRateRepository.save(rate2);
	}

}

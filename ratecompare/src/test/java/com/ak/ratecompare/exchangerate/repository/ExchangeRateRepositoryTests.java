package com.ak.ratecompare.exchangerate.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ak.ratecompare.exchangerate.model.ExchangeRate;

@SpringBootTest
public class ExchangeRateRepositoryTests {

	@Autowired
	private ExchangeRateRepository exchangeRateRepository;

	@Test
	public void whenFindByCurrencyPair_thenReturnRates() {
		
		List<ExchangeRate> rates = exchangeRateRepository.findBySourceCurrencyAndTargetCurrencyOrderByTimestampDesc("USD", "EUR");
		assertThat(rates).isNotEmpty();
	}
	
	@Test
	public void whenFindByCurrencyPairAndProvider_thenReturnRates() {
		
		Optional<ExchangeRate> rateOptional = exchangeRateRepository.findBySourceCurrencyAndTargetCurrencyAndProviderOrderByTimestampDesc("USD", "EUR", "Wise");
	
		assertThat(rateOptional).isPresent();
	    assertThat(rateOptional.get().getProvider()).isEqualTo("Wise");
	}
}

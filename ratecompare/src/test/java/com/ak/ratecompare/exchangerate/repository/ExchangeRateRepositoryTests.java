package com.ak.ratecompare.exchangerate.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ak.ratecompare.exchangerate.model.ExchangeRate;
import com.ak.ratecompare.exchangerate.model.Provider;

@SpringBootTest
public class ExchangeRateRepositoryTests {

	@Autowired
	private ExchangeRateRepository exchangeRateRepository;
	
	@Autowired
	private ProviderRepository providerRepository;

	@Test
	public void whenFindByCurrencyPair_thenReturnRates() {
		
		List<ExchangeRate> rates = exchangeRateRepository.findBySourceCurrencyAndTargetCurrencyOrderByTimestampDesc("USD", "EUR");
		assertThat(rates).isNotEmpty();
	}
	
	@Test
	public void whenFindByCurrencyPairAndProvider_thenReturnRates() {
		
		Provider wiseProvider = providerRepository.findById("Wise")
				.orElseThrow(() -> new RuntimeException("Provider not Found!"));
		
		Optional<ExchangeRate> rateOptional = exchangeRateRepository.findTopBySourceCurrencyAndTargetCurrencyAndProviderNameOrderByTimestampDesc("USD", "EUR", wiseProvider.getName());
	
		assertThat(rateOptional).isPresent();
	    assertThat(rateOptional.get().getProvider()).isEqualTo("Wise");
	}
	
	@Test
	public void whenFindByCurrencyPairAndProvider_thenReturnRatesWithTestProvider() {
		
		Provider testProvider = new Provider("TestProvider", "https://api.test.com", "testApiKey", null, null);
		ExchangeRate testRate = new ExchangeRate("USD", "EUR", testProvider, new BigDecimal("0.95"), LocalDateTime.now());
		
		Optional<ExchangeRate> rateOptional = exchangeRateRepository.findTopBySourceCurrencyAndTargetCurrencyAndProviderNameOrderByTimestampDesc("USD", "EUR", "TestProvider");
	
		assertThat(rateOptional).isPresent();
	    assertThat(rateOptional.get().getProvider()).isEqualTo("TestProvider");
	}
}

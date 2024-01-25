package com.ak.ratecompare.exchangerate.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.ak.ratecompare.exchangerate.apiclients.ExchangeRateApiClient;
import com.ak.ratecompare.exchangerate.model.ExchangeRate;
import com.ak.ratecompare.exchangerate.model.Provider;
import com.ak.ratecompare.exchangerate.service.CurrencyExchangeService;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ExchangeRateRepositoryTests {

	@Mock
	private ExchangeRateRepository exchangeRateRepository;
	
	@Mock
	private ProviderRepository providerRepository;
	
	@Mock
	private ExchangeRateApiClient client;

	
	@InjectMocks
    private CurrencyExchangeService currencyExchangeService;
	
	@Test
	public void createProviderEntity() {
	    Provider provider = new Provider("Wise", "https://api.wise.com", "wiseApiKey", null, null);

	    assertNotNull(provider);
	    assertEquals("Wise", provider.getName());
	    assertEquals("https://api.wise.com", provider.getApiUrl());
	}
	
	@Test
	public void createExchangeRateEntity() {
	    Provider provider = new Provider("TestProvider", "http://test.com", "testKey", null, null);
	    ExchangeRate rate = new ExchangeRate("USD", "EUR", provider, BigDecimal.valueOf(1.1), LocalDateTime.now());
	    
	    assertNotNull(rate);
	    assertEquals("USD", rate.getSourceCurrency());
	    assertEquals("EUR", rate.getTargetCurrency());
	    assertEquals(BigDecimal.valueOf(1.1), rate.getRate());
	}
	
	

	// Must test JPAs
	
//	@Test
//	public void saveAndFindExchangeRate() {
//	    Provider provider = new Provider("TestProvider", "http://test.com", "testKey", null, null);
//	    providerRepository.save(provider);
//	    ExchangeRate savedRate = exchangeRateRepository.save(new ExchangeRate("USD", "EUR", provider, BigDecimal.valueOf(1.1), LocalDateTime.now()));
//	    
//	    Optional<ExchangeRate> foundRate = exchangeRateRepository.findById(savedRate.getId());
//	    
//	    assertTrue(foundRate.isPresent());
//	    assertEquals(BigDecimal.valueOf(1.1), foundRate.get().getRate());
//	}
	
	@Test
	public void whenProviderNotFound_thenExceptionThrown() {
	    assertThrows(RuntimeException.class, () -> {
	        currencyExchangeService.getExchangeRate("USD", "EUR", "NonExistentProvider");
	    });
	}
	
	@Test
    public void whenFindByCurrencyPair_thenReturnRates() {
        List<ExchangeRate> mockRates = Arrays.asList(
            new ExchangeRate("USD", "EUR", new Provider("Wise", "https://api.wise.com", "wiseApiKey", null, null), new BigDecimal("1.1"), LocalDateTime.now())
        );

        when(exchangeRateRepository.findBySourceCurrencyAndTargetCurrencyOrderByTimestampDesc("USD", "EUR")).thenReturn(mockRates);

        List<ExchangeRate> rates = exchangeRateRepository.findBySourceCurrencyAndTargetCurrencyOrderByTimestampDesc("USD", "EUR");
        assertThat(rates).isNotEmpty();
        assertThat(rates.get(0).getSourceCurrency()).isEqualTo("USD");
        assertThat(rates.get(0).getTargetCurrency()).isEqualTo("EUR");
    }

	
	@Test
    public void whenFindByCurrencyPairAndProvider_thenReturnRates() {
		
		String sourceCurrency = "USD";
        String targetCurrency = "EUR";
        String providerName = "Wise";
        Provider wiseProvider = new Provider(providerName, "https://api.wise.com", "wiseApiKey", null, null);
        ExchangeRate testRate = new ExchangeRate(sourceCurrency, targetCurrency, wiseProvider, new BigDecimal("1.1"), LocalDateTime.now());

        when(providerRepository.findById(providerName)).thenReturn(Optional.of(wiseProvider));
        when(exchangeRateRepository.findTopBySourceCurrencyAndTargetCurrencyAndProviderNameOrderByTimestampDesc(sourceCurrency, targetCurrency, providerName))
            .thenReturn(Optional.of(testRate));

        ExchangeRate result = currencyExchangeService.getExchangeRate(sourceCurrency, targetCurrency, providerName);

        assertNotNull(result);
        assertEquals("Wise", result.getProvider().getName());
        assertEquals(new BigDecimal("1.1"), result.getRate());
    }
	
	@Test
	public void whenFindByCurrencyPairAndProvider_thenReturnRatesWithTestProvider() {
		
		Provider testProvider = new Provider("TestProvider", "https://api.test.com", "testApiKey", null, null);
        ExchangeRate testRate = new ExchangeRate("USD", "EUR", testProvider, new BigDecimal("0.95"), LocalDateTime.now());
        
        when(exchangeRateRepository.findTopBySourceCurrencyAndTargetCurrencyAndProviderNameOrderByTimestampDesc("USD", "EUR", "TestProvider"))
            .thenReturn(Optional.of(testRate));
		
		Optional<ExchangeRate> rateOptional = exchangeRateRepository.findTopBySourceCurrencyAndTargetCurrencyAndProviderNameOrderByTimestampDesc("USD", "EUR", "TestProvider");
	
		assertThat(rateOptional).isPresent();
	    assertThat(rateOptional.get().getProvider().getName()).isEqualTo("TestProvider");
	    assertThat(rateOptional.get().getRate()).isEqualTo(new BigDecimal("0.95"));
	}
	
	@Test
	public void whenProviderNotFound_thenThrowException() {
	    String sourceCurrency = "USD";
	    String targetCurrency = "EUR";
	    String providerName = "UnknownProvider";

	    when(providerRepository.findById(providerName)).thenReturn(Optional.empty());

	    Exception exception = assertThrows(RuntimeException.class, () -> {
	        currencyExchangeService.getExchangeRate(sourceCurrency, targetCurrency, providerName);
	    });

	    assertEquals("Provider not found", exception.getMessage());
	}
	
}

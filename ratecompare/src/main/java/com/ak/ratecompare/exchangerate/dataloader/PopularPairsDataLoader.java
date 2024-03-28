package com.ak.ratecompare.exchangerate.dataloader;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.ak.ratecompare.exchangerate.cache.CurrencyCache;
import com.ak.ratecompare.exchangerate.events.CacheLoadedEvent;
import com.ak.ratecompare.exchangerate.model.Currency;
import com.ak.ratecompare.exchangerate.model.midMarket.CurrencyExchangeRate;
import com.ak.ratecompare.exchangerate.repository.CurrencyExchangeRateRepository;

@Component
@Order(4)
public class PopularPairsDataLoader {
	
	@Autowired
    private CurrencyExchangeRateRepository currencyExchangeRateRepository;

    @Autowired
    private CurrencyCache currencyCache;

    @EventListener(CacheLoadedEvent.class)
    public void onCacheLoadedEvent() {

        // Preloading exchange rates for popular pairs
        preloadExchangeRates("USD", "EUR");
        preloadExchangeRates("GBP", "USD");
        preloadExchangeRates("EUR", "JPY");
        
        preloadExchangeRates("USD", "GBP");
        preloadExchangeRates("GBP", "EUR");
        preloadExchangeRates("EUR", "GBP");
    }

    private void preloadExchangeRates(String baseCode, String targetCode) {
        Currency baseCurrency = currencyCache.getCurrencyByCode(baseCode);
        Currency targetCurrency = currencyCache.getCurrencyByCode(targetCode);

        if (baseCurrency == null || targetCurrency == null) {
            throw new RuntimeException("One of the currencies not found in cache: " + baseCode + ", " + targetCode);
        }
        
        BigDecimal randomRate = BigDecimal.valueOf(0.5 + (Math.random()*(1)));
        CurrencyExchangeRate currencyExchangeRate = new CurrencyExchangeRate(baseCurrency, targetCurrency, randomRate, LocalDateTime.now());
        currencyExchangeRateRepository.save(currencyExchangeRate);
    }

}

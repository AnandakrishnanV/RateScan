package com.ak.ratecompare.exchangerate.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.ak.ratecompare.exchangerate.events.CacheLoadedEvent;
import com.ak.ratecompare.exchangerate.model.Currency;
import com.ak.ratecompare.exchangerate.repository.CurrencyRepository;

import jakarta.annotation.PostConstruct;

@Component
public class CurrencyCache {

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	private final CurrencyRepository currencyRepository;
	private final ConcurrentHashMap<String, Currency> currencyCache = new ConcurrentHashMap<>();

	// Constructor Injection
	public CurrencyCache(CurrencyRepository currencyRepository) {
		this.currencyRepository = currencyRepository;
	}

	
	public void init() {
		loadCache();

		eventPublisher.publishEvent(new CacheLoadedEvent(this));
	}

	public void loadCache() {
		System.out.println("here");
		List<Currency> currencies = currencyRepository.findAll();
		System.out.println(currencies);
		currencies.forEach(currency -> currencyCache.put(currency.getCurrencyCode(), currency));
	}

	public Currency getCurrencyByCode(String code) {
		return currencyCache.get(code);
	}

	public List<Currency> getAllCurrencies() {
		return new ArrayList<>(currencyCache.values());
	}
}

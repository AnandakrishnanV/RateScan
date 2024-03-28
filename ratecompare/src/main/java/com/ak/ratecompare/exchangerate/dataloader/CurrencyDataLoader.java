package com.ak.ratecompare.exchangerate.dataloader;

import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.ak.ratecompare.exchangerate.cache.CurrencyCache;
import com.ak.ratecompare.exchangerate.model.Currency;
import com.ak.ratecompare.exchangerate.repository.CurrencyRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Order(3)
public class CurrencyDataLoader implements CommandLineRunner {

	@Autowired
	private CurrencyRepository currencyRepository;

	@Autowired
    private ResourceLoader resourceLoader;
	
	@Autowired
    private CurrencyCache currencyCache;

	@Override
    public void run(String... args) throws Exception {
		System.out.println("hi");
        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = resourceLoader.getResource("classpath:data/currency_details.json").getInputStream();
        List<Currency> currencies = mapper.readValue(inputStream, new TypeReference<List<Currency>>(){});
        
        // Checking if table empty
        if (currencyRepository.count() == 0) {
            currencyRepository.saveAll(currencies);
        }
        currencyCache.init();
    }

}

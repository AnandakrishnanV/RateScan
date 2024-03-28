package com.ak.ratecompare.exchangerate.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class AppConfig {

	private List<String> popularCurrencies;

	public List<String> getPopularCurrencies() {
		return popularCurrencies;
	}

	public void getPopularCurrencies(String popularCurrencies) {
		this.popularCurrencies = Arrays.asList(popularCurrencies.split(","));
	}
	
	public void setPopularCurrencies(List<String> popularCurrencies) {
        this.popularCurrencies = popularCurrencies;
    }

}

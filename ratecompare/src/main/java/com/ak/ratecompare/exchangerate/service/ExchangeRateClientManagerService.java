package com.ak.ratecompare.exchangerate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ak.ratecompare.exchangerate.interfaces.ExchangeRateApiClient;
import com.ak.ratecompare.exchangerate.model.Provider;

@Service
public class ExchangeRateClientManagerService {

	@Autowired
	private List<ExchangeRateApiClient> apiClients;

	public ExchangeRateApiClient findClientByName(Provider provider) {

		return apiClients.stream()
				.filter(client -> client.getProviderName().equals(provider.getName()))
				.findFirst()
				.orElse(null);
	}
	
	public List<ExchangeRateApiClient> getAllClients() {
		return apiClients;
	}
}

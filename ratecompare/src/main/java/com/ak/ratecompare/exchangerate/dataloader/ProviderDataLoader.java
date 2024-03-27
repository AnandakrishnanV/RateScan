package com.ak.ratecompare.exchangerate.dataloader;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.ak.ratecompare.exchangerate.model.Provider;
import com.ak.ratecompare.exchangerate.repository.ProviderRepository;

@Component
@Order(1)
public class ProviderDataLoader implements CommandLineRunner {
	
//	@Value("${wise.api.url}")
//    private String wiseApiUrl;
//    @Value("${wise.api.key}")
//    private String wiseApiKey;
    
    @Value("${wise.testapi.url}")
    private String wiseApiUrl;
    @Value("${wise.testapi.key}")
    private String wiseApiKey;

    @Value("${revolut.api.url}")
    private String revolutApiUrl;
    @Value("${revolut.api.key}")
    private String revolutApiKey;
    
    @Value("${remitly.api.url}")
    private String remitlyApiUrl;
    @Value("${remitly.api.key}")
    private String remitlyApiKey;
    
    private final ProviderRepository providerRepository;

	public ProviderDataLoader(ProviderRepository providerRepository) {
		this.providerRepository = providerRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		List<Provider> providers = Arrays
				.asList(new Provider("Wise", wiseApiUrl, wiseApiKey, null, LocalDateTime.now()),
						new Provider("Revolut", revolutApiUrl, revolutApiKey, null, LocalDateTime.now()),
						new Provider("Remitly", remitlyApiUrl, remitlyApiKey, null, LocalDateTime.now())
						);
		
		for (Provider provider: providers) {
			providerRepository.findById(provider.getName()).ifPresentOrElse(existingProvider -> updateProvider(existingProvider, provider), () -> providerRepository.save(provider));
		}
	}
	
	public void updateProvider(Provider existingProvider, Provider newProvider) {
		existingProvider.setApiKey(newProvider.getApiKey());
		existingProvider.setApiUrl(newProvider.getApiUrl());
		
		providerRepository.save(existingProvider);
	}

}

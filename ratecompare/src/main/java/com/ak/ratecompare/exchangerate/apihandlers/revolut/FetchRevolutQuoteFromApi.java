package com.ak.ratecompare.exchangerate.apihandlers.revolut;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.ak.ratecompare.exchangerate.model.Provider;
import com.ak.ratecompare.exchangerate.model.exchangeRateQuote.ExchangeRateQuote;
import com.ak.ratecompare.exchangerate.repository.ProviderRepository;
import com.fasterxml.jackson.databind.JsonNode;

import reactor.core.publisher.Mono;

@Component
public class FetchRevolutQuoteFromApi {

	@Autowired
	private WebClient.Builder webClientBuilder;

	@Autowired
	private ProviderRepository providerRepository;
	
	@Autowired
	private HandleRevolutResponse handleRevolutResponse;

	public ExchangeRateQuote fetchQuote(String sourceCurrency, String targetCurrency, Double sourceAmount,
			Double targetAmount) {

		WebClient webClient = webClientBuilder.build();

		// TO Change
		Provider revolutProvider = providerRepository.findById("Revolut")
				.orElseThrow(() -> new RuntimeException("Provider not found"));

		String url = UriComponentsBuilder.fromUriString(revolutProvider.getApiUrl())
        		.path("/exchange/quote")
        		.queryParam("amount", sourceAmount.intValue()) // MUST BE int for somereason
        		.queryParam("country", "GB") // Hard coding, since we only want rate
        		.queryParam("fromCurrency", sourceCurrency)
        		.queryParam("isRecipientAmount", false)	// Hard coding, since we only want rate
        		.queryParam("toCurrency", targetCurrency)
        		.toUriString();
        
        Mono<JsonNode> responseMono = webClient.get()
        		.uri(url)
        		.header(HttpHeaders.ACCEPT_LANGUAGE, "en")
                .retrieve()
                .bodyToMono(JsonNode.class);
        
        JsonNode responseJson = responseMono.block();
		
        if (responseJson != null) {
			return handleRevolutResponse.handleApiResponse(responseJson, revolutProvider);
		}
		else {
    		throw new RuntimeErrorException(null, "Source and Target Do Not Match");
    	}

	}
}

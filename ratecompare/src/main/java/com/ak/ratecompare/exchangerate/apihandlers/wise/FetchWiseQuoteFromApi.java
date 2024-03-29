package com.ak.ratecompare.exchangerate.apihandlers.wise;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.ak.ratecompare.exchangerate.model.Provider;
import com.ak.ratecompare.exchangerate.model.exchangeRateQuote.ExchangeRateQuote;
import com.ak.ratecompare.exchangerate.model.exchangeRateQuote.QuoteRequestDTO;
import com.ak.ratecompare.exchangerate.repository.ProviderRepository;
import com.fasterxml.jackson.databind.JsonNode;

import reactor.core.publisher.Mono;

@Component
public class FetchWiseQuoteFromApi {

	@Autowired
	private WebClient.Builder webClientBuilder;

	@Autowired
	private ProviderRepository providerRepository;
	
	@Autowired
	private HandleWiseResponse handleWiseResponse;

	public ExchangeRateQuote fetchQuote(String sourceCurrency, String targetCurrency, Double sourceAmount,
			Double targetAmount) {

		WebClient webClient = webClientBuilder.build();

		// TO Change
		Provider wiseProvider = providerRepository.findById("Wise")
				.orElseThrow(() -> new RuntimeException("Provider not found"));

		String url = wiseProvider.getApiUrl() + "/v3/quotes";
		QuoteRequestDTO request = new QuoteRequestDTO(sourceCurrency, targetCurrency, sourceAmount, targetAmount);

		Mono<JsonNode> responseMono = webClient.post().uri(url)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + wiseProvider.getApiKey())
				.contentType(MediaType.APPLICATION_JSON).bodyValue(request).retrieve().bodyToMono(JsonNode.class);

		JsonNode responseJson = responseMono.block();
		
		if (responseJson != null) {
			return handleWiseResponse.handleApiResponse(responseJson, wiseProvider);
		}
		else {
    		throw new RuntimeErrorException(null, "Source and Target Do Not Match");
    	}

	}
}

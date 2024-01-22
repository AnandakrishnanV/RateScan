package com.ak.ratecompare.exchangerate.apihandlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.ak.ratecompare.exchangerate.model.Provider;
import com.ak.ratecompare.exchangerate.model.exchangeRateQuote.ExchangeRateQuote;
import com.ak.ratecompare.exchangerate.model.exchangeRateQuote.QuoteRequestDTO;
import com.ak.ratecompare.exchangerate.repository.ProviderRepository;

import reactor.core.publisher.Mono;

@Component
public class FetchWiseQuoteFromApi {

	@Autowired
	private WebClient.Builder webClientBuilder;

	@Autowired
	private ProviderRepository providerRepository;

	public Mono<ExchangeRateQuote> fetchQuote(String sourceCurrency, String targetCurrency, Double sourceAmout,
			Double targetAmount) {

		WebClient webClient = webClientBuilder.build();

		// TO Change
		Provider wiseProvider = providerRepository.findById("Wise")
				.orElseThrow(() -> new RuntimeException("Provider not found"));

		String url = wiseProvider.getApiUrl() + "/v3/quotes";
		QuoteRequestDTO request = new QuoteRequestDTO(sourceCurrency, targetCurrency, sourceAmout, targetAmount);
		
		return webClient.post()
				.uri(url)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + wiseProvider.getApiKey())
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(request)
                .retrieve()
                .bodyToMono(ExchangeRateQuote.class);

	}
}

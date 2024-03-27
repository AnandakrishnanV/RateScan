package com.ak.ratecompare.exchangerate.apihandlers;

import java.io.IOException;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.ak.ratecompare.exchangerate.model.CountryCurrencyInfo;
import com.ak.ratecompare.exchangerate.model.Provider;
import com.ak.ratecompare.exchangerate.model.exchangeRateQuote.ExchangeRateQuote;
import com.ak.ratecompare.exchangerate.repository.ProviderRepository;
import com.ak.ratecompare.exchangerate.service.CountryCurrencyService;
import com.ak.ratecompare.exchangerate.util.JsonUtil;
import com.fasterxml.jackson.databind.JsonNode;

import reactor.core.publisher.Mono;

@Component
public class FetchRemitlyQuoteFromApi {

	@Autowired
	private WebClient.Builder webClientBuilder;

	@Autowired
	private ProviderRepository providerRepository;

	@Autowired
	private CountryCurrencyService countryCurrencyService;

	@Autowired
	private HandleRemitlyResponse handleRemitlyResponse;

	public ExchangeRateQuote fetchQuote(String sourceCurrency, String targetCurrency, Double sourceAmount,
			Double targetAmount, String sourceCountry, String targetCountry) {

		WebClient webClient = webClientBuilder.build();

		// TO Change
		Provider remitlyProvider = providerRepository.findById("Remitly")
				.orElseThrow(() -> new RuntimeException("Provider not found"));

		String amountString = sourceAmount + " " + sourceCurrency;
		

		String url = UriComponentsBuilder.fromUriString(remitlyProvider.getApiUrl()).path("pricing/estimates")
				.queryParam("amount", amountString).queryParam("anchor", "SEND") // Hard coding
				.queryParam("conduit", getRemitlyFormatCountryCurrenyString(sourceCurrency, targetCurrency, sourceCountry, targetCountry))
				.build(false).toUriString();																										// so doing build false to get around it
		
		Mono<JsonNode> responseMono = webClient.get().uri(url).retrieve()
				.bodyToMono(JsonNode.class);

		JsonNode responseJson = responseMono.block();

		if (responseJson != null) {
			return handleRemitlyResponse.handleApiResponse(responseJson, remitlyProvider);
		} else {
			throw new RuntimeErrorException(null, "Source and Target Do Not Match");
		}

	}

	public String getRemitlyFormatCountryCurrenyString(String sourceCurrency, String targetCurrency, String sourceCountry, String targetCountry) {
		
		// Example GBR:GBP-USA:USD
		String sourceFormat = countryCurrencyService.appendCountryIso3ToCurrencyIso3(sourceCountry, sourceCurrency);
		String targetFormat = countryCurrencyService.appendCountryIso3ToCurrencyIso3(targetCountry, targetCurrency);

		return sourceFormat + "-" + targetFormat;
	}

}

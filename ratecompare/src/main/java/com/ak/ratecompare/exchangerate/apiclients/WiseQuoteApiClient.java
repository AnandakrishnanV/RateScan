package com.ak.ratecompare.exchangerate.apiclients;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.ak.ratecompare.exchangerate.model.Provider;
import com.ak.ratecompare.exchangerate.model.exchangeRateQuote.ExchangeQuoteOptions;
import com.ak.ratecompare.exchangerate.model.exchangeRateQuote.ExchangeRateQuote;
import com.ak.ratecompare.exchangerate.model.exchangeRateQuote.QuoteRequestDTO;
import com.ak.ratecompare.exchangerate.repository.ExchangeRateQuoteRepository;
import com.ak.ratecompare.exchangerate.repository.ProviderRepository;
import com.fasterxml.jackson.databind.JsonNode;

import reactor.core.publisher.Mono;

@Component
public class WiseQuoteApiClient implements ExchangeRateQuoteApiClient {

	@Autowired
	private WebClient.Builder webClientBuilder;

	@Autowired
	private ProviderRepository providerRepository;
	
	@Autowired
	private ExchangeRateQuoteRepository exchangeRateQuoteRepository;
	
	@Override
	public String getProviderName() {
		return "Wise";
	}

	@Override
	public ExchangeRateQuote fetchRate(String sourceCurrency, String targetCurrency, Double sourceAmout,
			Double targetAmount) {

		WebClient webClient = webClientBuilder.build();

		// TO Change
		Provider wiseProvider = providerRepository.findById("Wise")
				.orElseThrow(() -> new RuntimeException("Provider not found"));

		String url = wiseProvider.getApiUrl() + "/v3/quotes";
		QuoteRequestDTO request = new QuoteRequestDTO(sourceCurrency, targetCurrency, sourceAmout, targetAmount);

		Mono<JsonNode> responseMono = webClient.post().uri(url)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + wiseProvider.getApiKey())
				.contentType(MediaType.APPLICATION_JSON).bodyValue(request).retrieve().bodyToMono(JsonNode.class);

		JsonNode responseJson = responseMono.block();

		ExchangeRateQuote exchangeRateQuote = new ExchangeRateQuote();
		exchangeRateQuote.setSourceCurrency(responseJson.get("sourceCurrency").asText());
		exchangeRateQuote.setTargetCurrency(responseJson.get("targetCurrency").asText());
		exchangeRateQuote.setRate(new BigDecimal(responseJson.get("rate").asText()));
		exchangeRateQuote.setRateTimestamp(LocalDateTime.now());
		exchangeRateQuote.setExpirationTime(LocalDateTime.now());
		exchangeRateQuote.setProvider(wiseProvider);

		List<ExchangeQuoteOptions> options = new ArrayList<>();
		responseJson.get("paymentOptions").forEach(optionNode -> {
			ExchangeQuoteOptions option = new ExchangeQuoteOptions();
			option.setEstimatedDelivery(LocalDateTime.now());
			option.setPayIn(optionNode.get("payIn").asText());
			option.setPayOut(optionNode.get("payOut").asText());
			option.setFee(optionNode.get("fee").get("total").asDouble());
			option.setSourceAmount(optionNode.get("sourceAmount").asDouble());
			option.setTargetAmount(optionNode.get("targetAmount").asDouble());
			option.setExchangeRateQuote(exchangeRateQuote);
			options.add(option);
		});

		exchangeRateQuote.setExchangeQuoteOptions(options);

		return exchangeRateQuoteRepository.save(exchangeRateQuote);

	}
}

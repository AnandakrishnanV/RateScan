package com.ak.ratecompare.exchangerate.apihandlers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ak.ratecompare.exchangerate.model.Provider;
import com.ak.ratecompare.exchangerate.model.exchangeRateQuote.ExchangeQuoteOptions;
import com.ak.ratecompare.exchangerate.model.exchangeRateQuote.ExchangeRateQuote;
import com.fasterxml.jackson.databind.JsonNode;

@Service
public class HandleWiseResponse {
	
	public ExchangeRateQuote handleApiResponse(JsonNode responseJson, Provider wiseProvider) {
	    
		
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
		return exchangeRateQuote;
		
	}

}

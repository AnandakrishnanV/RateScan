package com.ak.ratecompare.exchangerate.apihandlers.revolut;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ak.ratecompare.exchangerate.model.Provider;
import com.ak.ratecompare.exchangerate.model.exchangeRateQuote.ExchangeQuoteOptions;
import com.ak.ratecompare.exchangerate.model.exchangeRateQuote.ExchangeRateQuote;
import com.ak.ratecompare.exchangerate.util.LocalDateParseUtil;
import com.fasterxml.jackson.databind.JsonNode;

@Service
public class HandleRevolutResponse {
	
	public ExchangeRateQuote handleApiResponse(JsonNode responseJson, Provider revolutProvider) {
	    
		
		ExchangeRateQuote exchangeRateQuote = new ExchangeRateQuote();
		exchangeRateQuote.setSourceCurrency(responseJson.get("sender").get("currency").asText());
		exchangeRateQuote.setTargetCurrency(responseJson.get("recipient").get("currency").asText());
		exchangeRateQuote.setRate(new BigDecimal(responseJson.get("rate").get("rate").asText()).setScale(4, RoundingMode.HALF_DOWN));
		exchangeRateQuote.setRateTimestamp(LocalDateParseUtil.convertUnixTimeStringToLocalDateTime(responseJson.get("rate").get("timestamp").asText()));
		exchangeRateQuote.setExpirationTime(LocalDateTime.now().plusMinutes(1));		// Arbitrarty, It's almost instant with App to app (For now)
		exchangeRateQuote.setProvider(revolutProvider);
		
		

		List<ExchangeQuoteOptions> options = new ArrayList<>();
		
		ExchangeQuoteOptions option = new ExchangeQuoteOptions();				// It only shows 1 for now
		option.setEstimatedDelivery(LocalDateTime.now());
		option.setPayIn("APP");
		option.setPayOut("BANK?");
		option.setFee(0.0);
		option.setSourceAmount(responseJson.get("sender").get("amount").asDouble());
		option.setTargetAmount(responseJson.get("recipient").get("amount").asDouble());
		option.setExchangeRateQuote(exchangeRateQuote);
		options.add(option);
		
		exchangeRateQuote.setExchangeQuoteOptions(options);
		return exchangeRateQuote;
		
	}
	
	

}


/* {
    "sender": {
        "amount": 100,
        "currency": "EUR"
    },
    "recipient": {
        "amount": 108,
        "currency": "USD"
    },
    "rate": {
        "from": "EUR",
        "to": "USD",
        "rate": 1.087272788409963416,
        "timestamp": 1705911356096
    },
    "fxTooltip": "Our rate (including any fees) is currently 0.13% worse than the European Central Bank (ECB) rate. This comparison rate is typically one of the best available.\n\nOur exchange fees help cover the cost of exchanging uncommon currencies and the uncertainty of making exchanges while the market is closed. You can find out more in our <feesLink>fees page</feesLink>",
    "plans": [too much random data inside it]
}
 * 
 * 
 * */

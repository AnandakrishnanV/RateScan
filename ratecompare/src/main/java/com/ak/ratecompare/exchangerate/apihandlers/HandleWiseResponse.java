package com.ak.ratecompare.exchangerate.apihandlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ak.ratecompare.exchangerate.model.exchangeRateQuote.ExchangeQuoteOptions;
import com.ak.ratecompare.exchangerate.model.exchangeRateQuote.ExchangeRateQuote;
import com.ak.ratecompare.exchangerate.repository.ExchangeRateQuoteRepository;

import reactor.core.publisher.Mono;

@Service
public class HandleWiseResponse {
	
	@Autowired
	private ExchangeRateQuoteRepository exchangeRateQuoteRepository;
	
	@Autowired
	private FetchWiseQuoteFromApi fetchWiseQuote;
	
	public ExchangeRateQuote handleApiResponse(String sourceCurrency, String targetCurrency, Double sourceAmout, Double targetAmount) {
		
	    Mono<ExchangeRateQuote> responseMono = fetchWiseQuote.fetchQuote(sourceCurrency, targetCurrency, sourceAmout, targetAmount)
	        .map(apiResponse -> {
	            ExchangeRateQuote exchangeRateQuote = new ExchangeRateQuote();
	            exchangeRateQuote.setSourceCurrency(apiResponse.getSourceCurrency());
	            exchangeRateQuote.setTargetCurrency(apiResponse.getTargetCurrency());
	            exchangeRateQuote.setRate(apiResponse.getRate());
	            exchangeRateQuote.setRateTimestamp(apiResponse.getRateTimestamp());
	            exchangeRateQuote.setExpirationTime(apiResponse.getExpirationTime());
	            exchangeRateQuote.setProvider(null);

	            apiResponse.getExchangeQuoteOptions().forEach(optionResponse -> {
	            	ExchangeQuoteOptions exchangeQuoteOptions = new ExchangeQuoteOptions();
	            	exchangeQuoteOptions.setEstimatedDelivery(optionResponse.getEstimatedDelivery());
	            	exchangeQuoteOptions.setPayIn(optionResponse.getPayIn());
	            	exchangeQuoteOptions.setPayOut(optionResponse.getPayOut());
	            	exchangeQuoteOptions.setSourceAmount(optionResponse.getSourceAmount());
	            	exchangeQuoteOptions.setTargetAmount(optionResponse.getTargetAmount());
	            	exchangeQuoteOptions.setFee(optionResponse.getFee());

	                exchangeRateQuote.addExchangeQuoteOptions(exchangeQuoteOptions);
	            });

	            return exchangeRateQuote;
	        });
	    
	    ExchangeRateQuote response = responseMono.block();
	    System.out.println(response.toString());
    	System.out.println(response.getSourceCurrency());
    	System.out.println(sourceCurrency == response.getSourceCurrency());
	    return response;
	}

}

package com.ak.ratecompare.exchangerate.apiclients;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.ak.ratecompare.exchangerate.model.ExchangeRate;
import com.ak.ratecompare.exchangerate.model.Provider;
import com.ak.ratecompare.exchangerate.repository.ProviderRepository;

import reactor.core.publisher.Mono;

@Component
public class WiseApiClient implements ExchangeRateApiClient{
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	@Autowired
    private ProviderRepository providerRepository;

	@Override
	public String getProviderName() {
		return "Wise";
	}

	@Override
	public ExchangeRate fetchRate(String sourceCurrency, String targetCurrency) {
		// Fetch the Wise provider from the database
        Provider wiseProvider = providerRepository.findById(getProviderName())
            .orElseThrow(() -> new RuntimeException("Provider not found"));
        
        WebClient webClient = webClientBuilder.build();
        
        String url = UriComponentsBuilder.fromUriString(wiseProvider.getApiUrl())
        		.path("/v1/rates")
        		.queryParam("source", sourceCurrency)
        		.queryParam("target", targetCurrency)
        		.toUriString();
        
        Mono<WiseExchangeRateResponse[]> responseMono = webClient.get()
        		.uri(url)
        		.header(HttpHeaders.AUTHORIZATION, "Bearer " + wiseProvider.getApiKey())
                .retrieve()
                .bodyToMono(WiseExchangeRateResponse[].class);
        
        WiseExchangeRateResponse[] responses = responseMono.block();
        
        if (responses != null && responses.length > 0) {
        	WiseExchangeRateResponse response = responses[0];
        	
//        	System.out.println(response.toString());
//        	System.out.println(response.getSource());
//        	System.out.println(sourceCurrency == response.getSource());
        	
        	if (sourceCurrency.equals(response.getSource()) && targetCurrency.equals(response.getTarget())) {
        		return new ExchangeRate(sourceCurrency, targetCurrency, wiseProvider, response.getRate(), response.getFormattedTimeToLocalDateTime());        		
        	}
        	else {
        		throw new RuntimeErrorException(null, "Source and Target Do Not ");
        	}
        }
        
        throw new RuntimeException("No exchange rate data received from Wise");
        // Dummy implementation - returns a predefined exchange rate
        // return new ExchangeRate(sourceCurrency, targetCurrency, wiseProvider, new BigDecimal("0.845435345").setScale(6, RoundingMode.HALF_UP), LocalDateTime.now());
	}
	
	private static class WiseExchangeRateResponse {
		
		private BigDecimal rate;
		private String source;
		private String target;
		private String time;
		
		public BigDecimal getRate() {
			return rate;
		}
		public String getSource() {
			return source;
		}
		public String getTarget() {
			return target;
		}
		public String getTime() {
			return time;
		}
		
		@Override
		public String toString() {
			return "WiseExchangeRateResponse [rate=" + rate + ", source=" + source + ", target=" + target + ", time="
					+ time + "]";
		}
		
		public LocalDateTime getFormattedTimeToLocalDateTime() {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");
			OffsetDateTime odt = OffsetDateTime.parse(this.time, formatter);
			return odt.atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
		}
		
	}
}

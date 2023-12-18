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
public class RevolutApiClient implements ExchangeRateApiClient{
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	@Autowired
    private ProviderRepository providerRepository;

	@Override
	public String getProviderName() {
		return "Revolut";
	}

	@Override
	public ExchangeRate fetchRate(String sourceCurrency, String targetCurrency) {
		// Fetch the Wise provider from the database
        Provider revolutProvider = providerRepository.findById(getProviderName())
            .orElseThrow(() -> new RuntimeException("Provider not found"));
        
        WebClient webClient = webClientBuilder.build();
        
        String url = UriComponentsBuilder.fromUriString(revolutProvider.getApiUrl())
        		.path("exchange/quote")
        		.queryParam("amount", 100) // Hard coding, since we only want rate
        		.queryParam("country", "IN") // Hard coding, since we only want rate
        		.queryParam("fromCurrency", sourceCurrency)
        		.queryParam("isRecipientAmount", false)	// Hard coding, since we only want rate
        		.queryParam("toCurrency", targetCurrency)
        		.toUriString();
        
        Mono<RevolutExchangeRateResponse[]> responseMono = webClient.get()
        		.uri(url)
        		//.header(HttpHeaders.AUTHORIZATION, "Bearer " + revolutProvider.getApiKey())
                .retrieve()
                .bodyToMono(RevolutExchangeRateResponse[].class);
        
        RevolutExchangeRateResponse[] responses = responseMono.block();
        
        if (responses != null && responses.length > 0) {
        	RevolutExchangeRateResponse response = responses[0];
        	
//        	System.out.println(response.toString());
//        	System.out.println(response.getSource());
//        	System.out.println(sourceCurrency == response.getSource());
        	
        	if (sourceCurrency.equals(response.getSource()) && targetCurrency.equals(response.getTarget())) {
        		return new ExchangeRate(sourceCurrency, targetCurrency, revolutProvider, response.getRate(), response.getFormattedTimeToLocalDateTime());        		
        	}
        	else {
        		throw new RuntimeErrorException(null, "Source and Target Do Not ");
        	}
        }
        
        throw new RuntimeException("No exchange rate data received from Wise");
        // Dummy implementation - returns a predefined exchange rate
        // return new ExchangeRate(sourceCurrency, targetCurrency, wiseProvider, new BigDecimal("0.845435345").setScale(6, RoundingMode.HALF_UP), LocalDateTime.now());
	}
	
	private static class RevolutExchangeRateResponse {
		
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
			return "RevolutExchangeRateResponse [rate=" + rate + ", source=" + source + ", target=" + target + ", time="
					+ time + "]";
		}
		
		public LocalDateTime getFormattedTimeToLocalDateTime() {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");
			OffsetDateTime odt = OffsetDateTime.parse(this.time, formatter);
			return odt.atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
		}
		
	}
}

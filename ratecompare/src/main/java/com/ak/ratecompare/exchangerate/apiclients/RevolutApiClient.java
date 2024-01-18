package com.ak.ratecompare.exchangerate.apiclients;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

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
        		.path("/exchange/quote")
        		.queryParam("amount", 100) // Hard coding, since we only want rate
        		.queryParam("country", "IN") // Hard coding, since we only want rate
        		.queryParam("fromCurrency", sourceCurrency)
        		.queryParam("isRecipientAmount", false)	// Hard coding, since we only want rate
        		.queryParam("toCurrency", targetCurrency)
        		.toUriString();
        
        Mono<RevolutExchangeRateResponse> responseMono = webClient.get()
        		.uri(url)
        		//.header(HttpHeaders.AUTHORIZATION, "Bearer " + revolutProvider.getApiKey())
        		.header(HttpHeaders.ACCEPT_LANGUAGE, "en")
                .retrieve()
                .bodyToMono(RevolutExchangeRateResponse.class);
        
        RevolutExchangeRateResponse responses = responseMono.block();
        
        if (responses != null) {
        	RevolutExchangeRateResponse response = responses;
        	
        	System.out.println(response.toString());
        	System.out.println(response.getFrom());
        	System.out.println(sourceCurrency == response.getFrom());
        	
        	if (sourceCurrency.equals(response.getFrom()) && targetCurrency.equals(response.getTo())) {
        		return new ExchangeRate(sourceCurrency, targetCurrency, revolutProvider, response.getRate(), response.getTimestamp());        		
        	}
        	else {
        		throw new RuntimeErrorException(null, "Source and Target Do Not Match");
        	}
        }
        
        throw new RuntimeException("No exchange rate data received from Revolut");
        
	}
	
	public static class RevolutExchangeRateResponse {
	    private RateDetails rate; // Corresponds to the "rate" part of the JSON
	    
	    public RevolutExchangeRateResponse() {
		}

	    public RevolutExchangeRateResponse(RateDetails rate) {
			super();
			this.rate = rate;
		}

		public BigDecimal getRate() {
	        return rate != null ? rate.getRate() : null;
	    }
	    
	    public String getFrom() {
	    	return rate != null ? rate.getFrom() : null;
	    }
	    
	    public String getTo() {
	    	return rate != null ? rate.getTo() : null;
	    }

	    public LocalDateTime getTimestamp() {
	        if (rate != null && rate.getTimestamp() != null) {
	            return rate.getFormattedTimeToLocalDateTime();
	        }
	        return null;
	    }

	    @Override
		public String toString() {
			return "RevolutExchangeRateResponse [rate=" + rate.toString();
		}



		private static class RateDetails {
	    	private BigDecimal rate;
			private String from;
			private String to;
			private String timestamp;
			
			public RateDetails() {
			}
			
			public RateDetails(BigDecimal rate, String from, String to, String timestamp) {
				super();
				this.rate = rate;
				this.from = from;
				this.to = to;
				this.timestamp = timestamp;
			}
			
			public BigDecimal getRate() {
				return rate;
			}
			public String getFrom() {
				return from;
			}
			public String getTo() {
				return to;
			}
			public String getTimestamp() {
				return timestamp;
			}
			
			@Override
			public String toString() {
				return "RevolutExchangeRateResponse [rate=" + rate + ", from=" + from + ", to=" + to + ", timestamp="
						+ timestamp + "]";
			}
			
			public LocalDateTime getFormattedTimeToLocalDateTime() {
//				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");
//				OffsetDateTime odt = OffsetDateTime.parse(this.timestamp, formatter);
//				return odt.atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
				
				if (this.timestamp != null) {
			        return LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(this.timestamp)), ZoneId.systemDefault());
			    }
			    return null;
			}
	    }

	    // Other fields and getters corresponding to the JSON structure
	}
	
}

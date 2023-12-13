package com.ak.ratecompare.exchangerate;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Validated
public class ExchangeRateRequest {

	@NotBlank(message = "Source currency is required")
	@Pattern(regexp = "[A-Z]{3}", message = "Invalid source currency format")
	private String sourceCurrency;

	@NotBlank(message = "Target currency is required")
	@Pattern(regexp = "[A-Z]{3}", message = "Invalid target currency format")
	private String targetCurrency;

	// Constructors
	public ExchangeRateRequest() {
	}

	public ExchangeRateRequest(String sourceCurrency, String targetCurrency) {
		this.sourceCurrency = sourceCurrency;
		this.targetCurrency = targetCurrency;
	}

	public String getSourceCurrency() {
		return sourceCurrency;
	}

	public void setSourceCurrency(String sourceCurrency) {
		this.sourceCurrency = sourceCurrency;
	}

	public String getTargetCurrency() {
		return targetCurrency;
	}

	public void setTargetCurrency(String targetCurrency) {
		this.targetCurrency = targetCurrency;
	}

	@Override
	public String toString() {
		return "ExchangeRateRequest [sourceCurrency=" + sourceCurrency + ", targetCurrency=" + targetCurrency + "]";
	}

}

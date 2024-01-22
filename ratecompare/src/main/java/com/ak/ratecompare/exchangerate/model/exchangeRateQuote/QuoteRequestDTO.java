package com.ak.ratecompare.exchangerate.model.exchangeRateQuote;

import java.math.BigDecimal;

public class QuoteRequestDTO {
	
	private String sourceCurrency;
    private String targetCurrency;
    private Double sourceAmount;
    private Double targetAmount;
    
    public QuoteRequestDTO () {
    	
    }

	public QuoteRequestDTO(String sourceCurrency, String targetCurrency, Double sourceAmount, Double targetAmount) {
		super();
		this.sourceCurrency = sourceCurrency;
		this.targetCurrency = targetCurrency;
		this.sourceAmount = sourceAmount;
		this.targetAmount = targetAmount;
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

	public Double getSourceAmount() {
		return sourceAmount;
	}

	public void setSourceAmount(Double sourceAmount) {
		this.sourceAmount = sourceAmount;
	}

	public Double getTargetAmount() {
		return targetAmount;
	}

	public void setTargetAmount(Double targetAmount) {
		this.targetAmount = targetAmount;
	}

	@Override
	public String toString() {
		return "QuoteRequestDTO [sourceCurrency=" + sourceCurrency + ", targetCurrency=" + targetCurrency
				+ ", sourceAmount=" + sourceAmount + ", targetAmount=" + targetAmount + "]";
	}
    
	
	
}

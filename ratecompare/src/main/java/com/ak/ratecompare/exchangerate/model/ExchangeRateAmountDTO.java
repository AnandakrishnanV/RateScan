package com.ak.ratecompare.exchangerate.model;

import java.math.BigDecimal;

public class ExchangeRateAmountDTO {
	
	private Long id;
	private String sourceCurrency;
    private String targetCurrency;
    private String providerName;
    private BigDecimal rate;
    private BigDecimal amount;
    private String timestamp;
    
    public ExchangeRateAmountDTO () {
    	
    }
    
	public ExchangeRateAmountDTO(Long id, String sourceCurrency, String targetCurrency, String providerName, BigDecimal rate, BigDecimal amount,
			String timestamp) {
		super();
		this.id = id;
		this.sourceCurrency = sourceCurrency;
		this.targetCurrency = targetCurrency;
		this.providerName = providerName;
		this.rate = rate;
		this.amount = amount;
		this.timestamp = timestamp;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "ExchangeRateAmountDTO [id=" + id + ", sourceCurrency=" + sourceCurrency + ", targetCurrency="
				+ targetCurrency + ", providerName=" + providerName + ", rate=" + rate + ", amount=" + amount
				+ ", timestamp=" + timestamp + "]";
	}
}

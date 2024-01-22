package com.ak.ratecompare.exchangerate.model.exchangeRateQuote;

import java.math.BigDecimal;
import java.util.List;

public class ExchangeRateQuoteResponseDTO {

	private Long id;
	private String sourceCurrency;
	private String targetCurrency;
	private String providerName;
	private BigDecimal rate;
	private String rateTimestamp;
	private String expirationTime;

	private List<ExchangeRateQuoteOptionsResponseDTO> paymentOptions;

	public ExchangeRateQuoteResponseDTO() {

	}

	public ExchangeRateQuoteResponseDTO(Long id, String sourceCurrency, String targetCurrency, String providerName,
			BigDecimal rate, String rateTimestamp, String expirationTime,
			List<ExchangeRateQuoteOptionsResponseDTO> paymentOptions) {
		super();
		this.id = id;
		this.sourceCurrency = sourceCurrency;
		this.targetCurrency = targetCurrency;
		this.providerName = providerName;
		this.rate = rate;
		this.rateTimestamp = rateTimestamp;
		this.expirationTime = expirationTime;
		this.paymentOptions = paymentOptions;
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

	public String getRateTimestamp() {
		return rateTimestamp;
	}

	public void setRateTimestamp(String rateTimestamp) {
		this.rateTimestamp = rateTimestamp;
	}

	public String getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(String expirationTime) {
		this.expirationTime = expirationTime;
	}

	public List<ExchangeRateQuoteOptionsResponseDTO> getPaymentOptions() {
		return paymentOptions;
	}

	public void setPaymentOptions(List<ExchangeRateQuoteOptionsResponseDTO> paymentOptions) {
		this.paymentOptions = paymentOptions;
	}

	@Override
	public String toString() {
		return "ExchangeRateQuoteResponseDTO [id=" + id + ", sourceCurrency=" + sourceCurrency + ", targetCurrency="
				+ targetCurrency + ", providerName=" + providerName + ", rate=" + rate + ", rateTimestamp="
				+ rateTimestamp + ", expirationTime=" + expirationTime + ", paymentOptions=" + paymentOptions + "]";
	}

}

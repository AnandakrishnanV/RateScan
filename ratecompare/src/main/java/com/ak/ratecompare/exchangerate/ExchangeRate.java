package com.ak.ratecompare.exchangerate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "exchange_rates")
public class ExchangeRate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "source_currency", nullable = false)
	private String sourceCurrency;

	@Column(name = "target_currency", nullable = false)
	private String targetCurrency;

	@Column(name = "currency_pair", nullable = false)
	private String currencyPair; // Concatenated source and target currencies

	@Column(name = "rate", nullable = false, precision = 10, scale = 4)
	private BigDecimal rate;

	@Column(name = "timestamp", nullable = false)
	private LocalDateTime timestamp;
	
	@Column(name = "provider", nullable = false)
	private String provider;

	public ExchangeRate() {

	}

	public ExchangeRate(Long id, String sourceCurrency, String targetCurrency, String currencyPair, BigDecimal rate,
			LocalDateTime timestamp, String provider) {
		super();
		this.id = id;
		this.sourceCurrency = sourceCurrency;
		this.targetCurrency = targetCurrency;
		this.currencyPair = currencyPair;
		this.rate = rate;
		this.timestamp = timestamp;
		this.provider = provider;
	}

	@PrePersist
	@PreUpdate
	private void prepareCurrencyPair() {
		this.currencyPair = sourceCurrency + "_" + targetCurrency;
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

	public String getCurrencyPair() {
		return currencyPair;
	}

	public void setCurrencyPair(String currencyPair) {
		this.currencyPair = currencyPair;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	@Override
	public String toString() {
		return "ExchangeRate [id=" + id + ", sourceCurrency=" + sourceCurrency + ", targetCurrency=" + targetCurrency
				+ ", currencyPair=" + currencyPair + ", rate=" + rate + ", timestamp=" + timestamp  + ", provider=" + provider + "]";
	}

	public boolean isStale(int cacheDurationInMinutes) {
		return this.timestamp.plusMinutes(cacheDurationInMinutes).isBefore(LocalDateTime.now());
	}

}

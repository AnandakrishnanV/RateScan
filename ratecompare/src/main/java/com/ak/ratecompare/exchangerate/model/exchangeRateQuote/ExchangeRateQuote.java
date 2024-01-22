package com.ak.ratecompare.exchangerate.model.exchangeRateQuote;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ak.ratecompare.exchangerate.model.Provider;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "exchange_rate_quote")
public class ExchangeRateQuote {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "source_currency", nullable = false)
	private String sourceCurrency;

	@Column(name = "target_currency", nullable = false)
	private String targetCurrency;
	
	@Column(name = "currency_pair", nullable = false)
	private String currencyPair; // Concatenated source and target currencies

	@Column(name = "rate", nullable = false, precision = 10, scale = 8)
	private BigDecimal rate;

	@Column(name = "timestamp", nullable = false)
	private LocalDateTime rateTimestamp;
	
	@Column(name = "expiration_time", nullable = false)
	private LocalDateTime expirationTime;
	
	@ManyToOne
    @JoinColumn(name = "provider", referencedColumnName = "name")
	private Provider provider;
	
	@OneToMany(mappedBy = "exchangeRateQuote", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExchangeQuoteOptions> exchangeQuoteOptions = new ArrayList<>();
	
	// add created date

	public List<ExchangeQuoteOptions> getExchangeQuoteOptions() {
        return exchangeQuoteOptions;
    }

    public void setExchangeQuoteOptions(List<ExchangeQuoteOptions> exchangeQuoteOptions) {
        this.exchangeQuoteOptions = exchangeQuoteOptions;
    }
	
    public void addExchangeQuoteOptions(ExchangeQuoteOptions exchangeQuoteOptions) {
    	this.exchangeQuoteOptions.add(exchangeQuoteOptions);
    	exchangeQuoteOptions.setExchangeRateQuote(this);
    }

	public ExchangeRateQuote() {

	}

	public ExchangeRateQuote(Long id, String sourceCurrency, String targetCurrency, String currencyPair,
			BigDecimal rate, LocalDateTime rateTimestamp, LocalDateTime expirationTime, Provider provider,
			List<ExchangeQuoteOptions> exchangeQuoteOptions) {
		super();
		this.id = id;
		this.sourceCurrency = sourceCurrency;
		this.targetCurrency = targetCurrency;
		this.currencyPair = currencyPair;
		this.rate = rate;
		this.rateTimestamp = rateTimestamp;
		this.expirationTime = expirationTime;
		this.provider = provider;
		this.exchangeQuoteOptions = exchangeQuoteOptions;
	}

	@PrePersist
	@PreUpdate
	private void prepareCurrencyPair() {
		this.currencyPair = sourceCurrency + "_" + targetCurrency;
	}
	
	public Long getId() {
		return id;
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

	public LocalDateTime getRateTimestamp() {
		return rateTimestamp;
	}

	public void setRateTimestamp(LocalDateTime rateTimestamp) {
		this.rateTimestamp = rateTimestamp;
	}

	public LocalDateTime getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(LocalDateTime expirationTime) {
		this.expirationTime = expirationTime;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public boolean isStale(int cacheDurationInMinutes) {
		return this.rateTimestamp.plusMinutes(cacheDurationInMinutes).isBefore(LocalDateTime.now());
	}

	@Override
	public String toString() {
		return "ExchangeRateQuote [id=" + id + ", sourceCurrency=" + sourceCurrency + ", targetCurrency="
				+ targetCurrency + ", currencyPair=" + currencyPair + ", rate=" + rate + ", rateTimestamp="
				+ rateTimestamp + ", expirationTime=" + expirationTime +", exchangeQuoteOptions=" + exchangeQuoteOptions + "]";
	}

	

	
}

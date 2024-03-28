package com.ak.ratecompare.exchangerate.model.midMarket;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.ak.ratecompare.exchangerate.model.Currency;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "currency_exchange_rate")
public class CurrencyExchangeRate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "base_currency_code", referencedColumnName = "currency_code", nullable = false)
	private Currency baseCurrency;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "target_currency_code", referencedColumnName = "currency_code", nullable = false)
	private Currency targetCurrency;

	@Column(name = "rate", nullable = false)
	private BigDecimal rate;

	@Column(name = "rate_date", nullable = false)
	private LocalDateTime rateDate;

	public CurrencyExchangeRate() {
	}

	public CurrencyExchangeRate(Currency baseCurrency, Currency targetCurrency, BigDecimal rate, LocalDateTime rateDate) {
		super();
		this.baseCurrency = baseCurrency;
		this.targetCurrency = targetCurrency;
		this.rate = rate;
		this.rateDate = rateDate;
	}

	public Currency getBaseCurrency() {
		return baseCurrency;
	}

	public void setBaseCurrency(Currency baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

	public Currency getTargetCurrency() {
		return targetCurrency;
	}

	public void setTargetCurrency(Currency targetCurrency) {
		this.targetCurrency = targetCurrency;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public LocalDateTime getRateDate() {
		return rateDate;
	}

	public void setRateDate(LocalDateTime rateDate) {
		this.rateDate = rateDate;
	}

	@Override
	public String toString() {
		return "CurrencyExchangeRate [id=" + id + ", baseCurrency=" + baseCurrency + ", targetCurrency="
				+ targetCurrency + ", rate=" + rate + ", rateDate=" + rateDate + "]";
	}

}

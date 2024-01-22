package com.ak.ratecompare.exchangerate.model.exchangeRateQuote;

import java.time.LocalDateTime;

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
@Table(name = "exchange_quote_options")
public class ExchangeQuoteOptions {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "estimated_delivery", nullable = false)
	private LocalDateTime estimatedDelivery;
	
	@Column(name = "pay_in", nullable = false)
	private String payIn;
	
	@Column(name = "pay_out", nullable = false)
	private String payOut;
	
	@Column(name = "source_amount", nullable = false)
	private Double sourceAmount;

	@Column(name = "target_amount", nullable = false)
	private Double targetAmount;
	
	@Column(name = "fee", nullable = false)
	private Double fee;
	
	@ManyToOne
    @JoinColumn(name = "exchange_rate_quote_id", nullable = false)
    private ExchangeRateQuote exchangeRateQuote;
	
	public ExchangeQuoteOptions() {
		
	}

	public ExchangeQuoteOptions(Long id, LocalDateTime estimatedDelivery, String payIn, String payOut, Double sourceAmount,
			Double targetAmount, Double fee, ExchangeRateQuote exchangeRateQuote) {
		super();
		this.id = id;
		this.estimatedDelivery = estimatedDelivery;
		this.payIn = payIn;
		this.payOut = payOut;
		this.sourceAmount = sourceAmount;
		this.targetAmount = targetAmount;
		this.fee = fee;
		this.exchangeRateQuote = exchangeRateQuote;
	}

	public Long getId() {
		return id;
	}

	public LocalDateTime getEstimatedDelivery() {
		return estimatedDelivery;
	}

	public void setEstimatedDelivery(LocalDateTime estimatedDelivery) {
		this.estimatedDelivery = estimatedDelivery;
	}

	public String getPayIn() {
		return payIn;
	}

	public void setPayIn(String payIn) {
		this.payIn = payIn;
	}

	public String getPayOut() {
		return payOut;
	}

	public void setPayOut(String payOut) {
		this.payOut = payOut;
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

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public ExchangeRateQuote getExchangeRateQuote() {
		return exchangeRateQuote;
	}

	public void setExchangeRateQuote(ExchangeRateQuote exchangeRateQuote) {
		this.exchangeRateQuote = exchangeRateQuote;
	}

	@Override
	public String toString() {
		return "ExchangeQuoteOptions [id=" + id + ", estimatedDelivery=" + estimatedDelivery + ", payIn=" + payIn
				+ ", payOut=" + payOut + ", sourceAmount=" + sourceAmount + ", targetAmount=" + targetAmount + ", fee="
				+ fee + "]";
	}
	
	
}

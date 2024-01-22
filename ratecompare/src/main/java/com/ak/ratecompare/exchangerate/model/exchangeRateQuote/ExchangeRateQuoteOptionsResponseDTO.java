package com.ak.ratecompare.exchangerate.model.exchangeRateQuote;

import java.math.BigDecimal;
import java.util.List;

public class ExchangeRateQuoteOptionsResponseDTO {

	private Long optionId;
	private String estimatedDelivery;
	private String payIn;
	private String payOut;
	private Double sourceAmount;
	private Double targetAmount;
	private Double fee;

	public ExchangeRateQuoteOptionsResponseDTO() {

	}

	public ExchangeRateQuoteOptionsResponseDTO(Long optionId, String estimatedDelivery, String payIn, String payOut,
			Double sourceAmount, Double targetAmount, Double fee) {
		super();
		this.optionId = optionId;
		this.estimatedDelivery = estimatedDelivery;
		this.payIn = payIn;
		this.payOut = payOut;
		this.sourceAmount = sourceAmount;
		this.targetAmount = targetAmount;
		this.fee = fee;
	}

	public Long getOptionId() {
		return optionId;
	}

	public void setOptionId(Long optionId) {
		this.optionId = optionId;
	}

	public String getEstimatedDelivery() {
		return estimatedDelivery;
	}

	public void setEstimatedDelivery(String estimatedDelivery) {
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

	@Override
	public String toString() {
		return "ExchangeRateQuoteOptionsResponseDTO [optionId=" + optionId + ", estimatedDelivery=" + estimatedDelivery
				+ ", payIn=" + payIn + ", payOut=" + payOut + ", sourceAmount=" + sourceAmount + ", targetAmount="
				+ targetAmount + ", fee=" + fee + "]";
	}
	
	

}

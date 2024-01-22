package com.ak.ratecompare.exchangerate.util;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ak.ratecompare.exchangerate.model.ExchangeRate;
import com.ak.ratecompare.exchangerate.model.ExchangeRateAmountDTO;
import com.ak.ratecompare.exchangerate.model.ExchangeRateDTO;
import com.ak.ratecompare.exchangerate.model.exchangeRateQuote.ExchangeRateQuote;
import com.ak.ratecompare.exchangerate.model.exchangeRateQuote.ExchangeRateQuoteOptionsResponseDTO;
import com.ak.ratecompare.exchangerate.model.exchangeRateQuote.ExchangeRateQuoteResponseDTO;

@Service
public class ExchangeRateMapper {

	public static ExchangeRateDTO toDTO(ExchangeRate exchangeRate) {

		System.out.println(exchangeRate.toString());

		ExchangeRateDTO dto = new ExchangeRateDTO();
		dto.setId(exchangeRate.getId());
		dto.setSourceCurrency(exchangeRate.getSourceCurrency());
		dto.setTargetCurrency(exchangeRate.getTargetCurrency());
		dto.setProviderName(exchangeRate.getProvider().getName());
		dto.setRate(exchangeRate.getRate());
		dto.setTimestamp(exchangeRate.getTimestamp().toString());
		return dto;
	}

	public static ExchangeRateAmountDTO toAmountDTO(ExchangeRate exchangeRate, int amount) {

		System.out.println(exchangeRate.toString());

		ExchangeRateAmountDTO dto = new ExchangeRateAmountDTO();
		dto.setId(exchangeRate.getId());
		dto.setSourceCurrency(exchangeRate.getSourceCurrency());
		dto.setTargetCurrency(exchangeRate.getTargetCurrency());
		dto.setProviderName(exchangeRate.getProvider().getName());
		dto.setRate(exchangeRate.getRate().setScale(4, RoundingMode.DOWN));
		dto.setAmount(ExchangeRateUtil.calculateAmount(exchangeRate.getRate(), amount).setScale(2, RoundingMode.DOWN));
		dto.setTimestamp(exchangeRate.getTimestamp().toString());
		return dto;
	}

	public static ExchangeRateQuoteResponseDTO toQuoteResponseDTO(ExchangeRateQuote exchangeRates) {
		
		ExchangeRateQuoteResponseDTO dto = new ExchangeRateQuoteResponseDTO();
		dto.setId(exchangeRates.getId());
		dto.setSourceCurrency(exchangeRates.getSourceCurrency());
		dto.setTargetCurrency(exchangeRates.getTargetCurrency());
		dto.setProviderName(exchangeRates.getProvider().getName());
		dto.setRate(exchangeRates.getRate());
		dto.setRateTimestamp(exchangeRates.getRateTimestamp().toString());
		dto.setExpirationTime(exchangeRates.getExpirationTime().toString());
		
		List<ExchangeRateQuoteOptionsResponseDTO> quoteOptions = new ArrayList<ExchangeRateQuoteOptionsResponseDTO>();
		
		exchangeRates.getExchangeQuoteOptions().forEach((quote) -> {
			ExchangeRateQuoteOptionsResponseDTO response = new ExchangeRateQuoteOptionsResponseDTO();
			response.setOptionId(quote.getId());
			response.setEstimatedDelivery(quote.getEstimatedDelivery().toString());
			response.setPayIn(quote.getPayIn());
			response.setPayOut(quote.getPayOut());
			response.setSourceAmount(quote.getSourceAmount());
			response.setTargetAmount(quote.getTargetAmount());
			response.setFee(quote.getFee());
			
			quoteOptions.add(response);
		});
		
		dto.setPaymentOptions(quoteOptions);
		
		return dto;

	}

}

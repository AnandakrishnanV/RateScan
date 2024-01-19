package com.ak.ratecompare.exchangerate.util;

import java.math.RoundingMode;

import org.springframework.stereotype.Service;

import com.ak.ratecompare.exchangerate.model.ExchangeRate;
import com.ak.ratecompare.exchangerate.model.ExchangeRateAmountDTO;
import com.ak.ratecompare.exchangerate.model.ExchangeRateDTO;

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
        dto.setRate(exchangeRate.getRate().setScale(4,RoundingMode.DOWN));
        dto.setAmount(ExchangeRateUtil.calculateAmount(exchangeRate.getRate(), amount).setScale(2,RoundingMode.DOWN));
        dto.setTimestamp(exchangeRate.getTimestamp().toString());
        return dto;
    }

}

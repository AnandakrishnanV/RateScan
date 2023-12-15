package com.ak.ratecompare.exchangerate.util;

import com.ak.ratecompare.exchangerate.model.ExchangeRate;
import com.ak.ratecompare.exchangerate.model.ExchangeRateDTO;

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

}

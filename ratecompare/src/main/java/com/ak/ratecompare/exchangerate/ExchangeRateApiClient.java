package com.ak.ratecompare.exchangerate;

import com.ak.ratecompare.exchangerate.model.ExchangeRate;

public interface ExchangeRateApiClient {

	String getProviderName();

	ExchangeRate fetchRate(String sourceCurrency, String targetCurrency);

}

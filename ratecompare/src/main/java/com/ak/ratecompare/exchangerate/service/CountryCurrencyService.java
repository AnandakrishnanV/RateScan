package com.ak.ratecompare.exchangerate.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ak.ratecompare.exchangerate.model.CountryCurrencyInfo;
import com.ak.ratecompare.exchangerate.util.JsonUtil;

@Service
public class CountryCurrencyService {

	public String findCountryIso3ByCountryIso2(String countryIso2) {
		System.out.println(countryIso2);
		try {
			List<CountryCurrencyInfo> countryCurrencyInfos = JsonUtil
					.loadListOfObjectsFromJson("data/currency_country.json", CountryCurrencyInfo.class);
			Optional<CountryCurrencyInfo> matchingInfo = countryCurrencyInfos.stream()
					.filter(info -> countryIso2.equals(info.getCountryIso2CharCode())).findFirst();
			return matchingInfo.map(CountryCurrencyInfo::getCountryIso3CharCode).orElse("USA");
		} catch (IOException e) {
			e.printStackTrace();
			return "Error occurred";
		}
	}

	// Remitly Style
	public String appendCountryIso3ToCurrencyIso3(String countryIso2, String currency) {
		return findCountryIso3ByCountryIso2(countryIso2) + ":" + currency;
	}
}

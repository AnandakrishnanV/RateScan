package com.ak.ratecompare.exchangerate.util;

import java.math.BigDecimal;


public class ExchangeRateUtil {

	public static BigDecimal calculateAmount(BigDecimal rate, int amount) {
		return rate.multiply(BigDecimal.valueOf(amount));
	}

}

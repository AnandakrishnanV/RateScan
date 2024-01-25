package com.ak.ratecompare.exchangerate.util;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


public class ExchangeRateUtil {

	public static BigDecimal calculateAmount(BigDecimal rate, int amount) {
		return rate.multiply(BigDecimal.valueOf(amount));
	}
	
	public static LocalDateTime getFormattedTimeToLocalDateTime(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");
		OffsetDateTime odt = OffsetDateTime.parse(date, formatter);
		return odt.atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
	}

}

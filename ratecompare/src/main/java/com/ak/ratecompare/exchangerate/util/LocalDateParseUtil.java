package com.ak.ratecompare.exchangerate.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class LocalDateParseUtil {
	
	public static LocalDateTime getZonedDateTime(String timestamp) {
		OffsetDateTime odt = OffsetDateTime.parse(timestamp);
		ZonedDateTime zdt = odt.atZoneSameInstant(ZoneId.systemDefault());
	    return zdt.toLocalDateTime();
	}
	
	public static LocalDateTime convertUnixTimeStringToLocalDateTime(String timestamp) {
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(timestamp)), ZoneId.systemDefault());
	}

}

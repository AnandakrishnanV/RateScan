package com.ak.ratecompare.exchangerate.apihandlers.remitly;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ak.ratecompare.exchangerate.model.Provider;
import com.ak.ratecompare.exchangerate.model.exchangeRateQuote.ExchangeQuoteOptions;
import com.ak.ratecompare.exchangerate.model.exchangeRateQuote.ExchangeRateQuote;
import com.ak.ratecompare.exchangerate.util.LocalDateParseUtil;
import com.fasterxml.jackson.databind.JsonNode;

@Service
public class HandleRemitlyResponse {

	public ExchangeRateQuote handleApiResponse(JsonNode responseJson, Provider remitlyProvider) {

		// Hardcoded stuff - assuming multiple: take rate of last i.e the higher
		// exchange rate
		// all numerics seem to be as a string with the currency attached, we dont need
		// it, can be inferred
		// so just removing all non numerics before dealing with it

		// Taking OpenBanking as base case: i.e size - 1 record
		JsonNode openBankingCase = responseJson.get(responseJson.size() - 1);

		ExchangeRateQuote exchangeRateQuote = new ExchangeRateQuote();
		exchangeRateQuote.setSourceCurrency(openBankingCase.get("base_currency").asText());
		exchangeRateQuote.setTargetCurrency(openBankingCase.get("quote_currency").asText());
		exchangeRateQuote.setRate(new BigDecimal(openBankingCase.get("exchange_rate_info").get("base_rate").asText())
				.setScale(4, RoundingMode.HALF_DOWN));
		exchangeRateQuote
				.setRateTimestamp(LocalDateParseUtil.getZonedDateTime(openBankingCase.get("created_at").asText()));
		exchangeRateQuote
				.setExpirationTime(LocalDateParseUtil.getZonedDateTime(openBankingCase.get("expires_at").asText()));
		exchangeRateQuote.setProvider(remitlyProvider);

		List<ExchangeQuoteOptions> options = new ArrayList<>();
		responseJson.forEach(optionNode -> {
			ExchangeQuoteOptions option = new ExchangeQuoteOptions();

			System.out.println(optionNode.toPrettyString());
			System.out.println("break");
			System.out.println(optionNode.get("send_amount"));

			option.setEstimatedDelivery(
					"EXPRESS".equalsIgnoreCase(optionNode.get("product_type").asText()) ? LocalDateTime.now()
							: "ECONOMY".equalsIgnoreCase(optionNode.get("product_type").asText())
									? LocalDateTime.now().plusDays(3)
									: LocalDateTime.now().plusDays(3));
			option.setPayIn(optionNode.get("payment_method").asText());

			// Bank Payment Assumed as not present in API
			option.setPayOut("To Bank Account");

			option.setFee(optionNode.get("fee_info").get("total_fee_amount").isTextual()
					? Double.parseDouble(
							optionNode.get("fee_info").get("total_fee_amount").asText().replaceAll("[^\\d.]", ""))
					: optionNode.get("fee_info").get("total_fee_amount").asDouble());

			option.setSourceAmount(optionNode.get("send_amount").isTextual()
					? Double.parseDouble(optionNode.get("send_amount").asText().replaceAll("[^\\d.]", ""))
					: optionNode.get("send_amount").asDouble());

			option.setTargetAmount(optionNode.get("receive_amount_before_adjustment").isTextual()
					? Double.parseDouble(
							optionNode.get("receive_amount_before_adjustment").asText().replaceAll("[^\\d.]", ""))
					: optionNode.get("receive_amount_before_adjustment").asDouble());

			// must add promo stuff
			option.setExchangeRateQuote(exchangeRateQuote);
			options.add(option);
		});

		exchangeRateQuote.setExchangeQuoteOptions(options);
		return exchangeRateQuote;

	}

}

//{
//	   "schema_version":1,
//	   "config_public_id":"GBR:GBP:USA:USD:EXPRESS:control:4776cd6ed1e8400caf268f73b51fda62",
//	   "quote_public_id":"6a770bd3cff64ef5b21cf81a4e011V21",
//	   "created_at":"2024-03-26T17:25:41.703Z",
//	   "expires_at":"2024-03-26T18:11:00Z",
//	   "base_currency":"GBP",
//	   "quote_currency":"USD",
//	   "source_country":"GBR",
//	   "target_country":"USA",
//	   "product_type":"EXPRESS",
//	   "package_name":"EXPRESS",
//	   "payment_method":"DEBIT",
//	   "send_amount":"560.00 GBP",
//	   "adjusted_send_amount":"560.00 GBP",
//	   "receive_amount_before_adjustment":"700.84 USD",
//	   "receive_amount_adjustment":"8.18 USD",
//	   "receive_amount":"709.02 USD",
//	   "total_charge_amount":"560.00 GBP",
//	   "charge_amount_before_adjustment":"561.99 GBP",
//	   "purpose":"OTHER",
//	   "applicable_promotion_info":{
//	      "total_promotion_effective_send_amount":"8.53 GBP",
//	      "applicable_promotions":[
//	         {
//	            "promo_code":"AUTO-EG-FREE-TXN-GBR-USA",
//	            "promotion_type":"FREE_TRANSACTION",
//	            "value":"1.99",
//	            "applied":true,
//	            "not_applied_reason":null
//	         },
//	         {
//	            "promo_code":"GBR-USA-MARKET-25BPS-1500CAP",
//	            "promotion_type":"PERCENT_BOOST_FOREX",
//	            "value":"1.2661",
//	            "applied":true,
//	            "not_applied_reason":null
//	         }
//	      ],
//	      "applied_promotions":[
//	         "AUTO-EG-FREE-TXN-GBR-USA",
//	         "GBR-USA-MARKET-25BPS-1500CAP"
//	      ]
//	   },
//	   "fee_info":{
//	      "product_fee_amount":"1.99 GBP",
//	      "payment_method_fee_amount":"0.00 GBP",
//	      "total_fee_amount":"1.99 GBP",
//	      "fee_breakdown":{
//	         "product_fixed_fee_amount":"1.99 GBP",
//	         "product_proportional_fee_percentage":"0.00000",
//	         "product_proportional_fee_amount":"0.00 GBP",
//	         "payment_method_fixed_fee_amount":"0.00 GBP",
//	         "payment_method_proportional_fee_percentage":"0.00",
//	         "payment_method_proportional_fee_amount":"0.00 GBP"
//	      },
//	      "fee_promos":{
//	         "product_fee_amount_discount":"1.99 GBP",
//	         "payment_method_fee_amount_discount":"0.00 GBP",
//	         "total_fee_amount_discount":"1.99 GBP"
//	      }
//	   },
//	   "exchange_rate_info":{
//	      "base_rate":"1.2515",
//	      "base_rate_id":"9cf0ba15ea904b8fa9fdae68128b2d31",
//	      "rate_promotion":{
//	         "promotional_rate":"1.2661",
//	         "capped_promotional_exchange_rate_amount":"1500.00 GBP",
//	         "receive_amount_using_base_rate":"0.00 USD",
//	         "receive_amount_using_promo_rate":"709.02 USD"
//	      },
//	      "cross":null
//	   },
//	   "send_amount_adjustments":{
//	      "send_amount_bonus":"0.00 GBP",
//	      "send_amount_discount":"0.00 GBP"
//	   },
//	   "rounding_info":null,
//	   "tax_display":[
//	      
//	   ]
//	}
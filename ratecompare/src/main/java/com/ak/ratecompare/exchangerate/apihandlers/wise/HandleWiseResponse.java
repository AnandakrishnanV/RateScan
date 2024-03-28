package com.ak.ratecompare.exchangerate.apihandlers.wise;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ak.ratecompare.exchangerate.model.Provider;
import com.ak.ratecompare.exchangerate.model.exchangeRateQuote.ExchangeQuoteOptions;
import com.ak.ratecompare.exchangerate.model.exchangeRateQuote.ExchangeRateQuote;
import com.ak.ratecompare.exchangerate.util.LocalDateParseUtil;
import com.fasterxml.jackson.databind.JsonNode;

@Service
public class HandleWiseResponse {

	public ExchangeRateQuote handleApiResponse(JsonNode responseJson, Provider wiseProvider) {

		ExchangeRateQuote exchangeRateQuote = new ExchangeRateQuote();
		exchangeRateQuote.setSourceCurrency(responseJson.get("sourceCurrency").asText());
		exchangeRateQuote.setTargetCurrency(responseJson.get("targetCurrency").asText());
		exchangeRateQuote
				.setRate(new BigDecimal(responseJson.get("rate").asText()).setScale(4, RoundingMode.HALF_DOWN));
		exchangeRateQuote
				.setRateTimestamp(LocalDateParseUtil.getZonedDateTime(responseJson.get("rateTimestamp").asText()));
		exchangeRateQuote
				.setExpirationTime(LocalDateParseUtil.getZonedDateTime(responseJson.get("expirationTime").asText()));
		exchangeRateQuote.setProvider(wiseProvider);

		List<ExchangeQuoteOptions> options = new ArrayList<>();
		responseJson.get("paymentOptions").forEach(optionNode -> {
			ExchangeQuoteOptions option = new ExchangeQuoteOptions();
			option.setEstimatedDelivery(
					LocalDateParseUtil.getZonedDateTime(optionNode.get("estimatedDelivery").asText()));
			option.setPayIn(optionNode.get("payIn").asText());
			option.setPayOut(optionNode.get("payOut").asText());
			option.setFee(optionNode.get("fee").get("total").asDouble());
			option.setSourceAmount(optionNode.get("sourceAmount").asDouble());
			option.setTargetAmount(optionNode.get("targetAmount").asDouble());
			option.setExchangeRateQuote(exchangeRateQuote);
			options.add(option);
		});

		exchangeRateQuote.setExchangeQuoteOptions(options);
		return exchangeRateQuote;

	}

}

/*
 * { "targetAmount": 200.50, "guaranteedTargetAmountAllowed": true,
 * "targetAmountAllowed": true, "paymentOptions": [ {
 * "formattedEstimatedDelivery": "by Monday", "estimatedDeliveryDelays": [],
 * "allowedProfileTypes": [ "PERSONAL", "BUSINESS" ], "payInProduct": "FAST",
 * "feePercentage": 0.0112, "estimatedDelivery": "2024-01-22T17:30:00Z",
 * "payOut": "BANK_TRANSFER", "disabled": false, "sourceAmount": 159.62,
 * "targetAmount": 200.50, "sourceCurrency": "GBP", "targetCurrency": "USD",
 * "fee": { "transferwise": 1.31, "payIn": 0.48, "discount": 0, "total": 1.79,
 * "priceSetId": 181, "partner": 0.0 }, "price": { "priceSetId": 181, "total": {
 * "type": "TOTAL", "label": "Total fees", "value": { "amount": 1.79,
 * "currency": "GBP", "label": "1.79 GBP" } }, "items": [ { "type":
 * "TRANSFERWISE", "label": "Our fee", "value": { "amount": 1.79, "currency":
 * "GBP", "label": "1.79 GBP" } } ] }, "payIn": "DEBIT" }, {
 * "formattedEstimatedDelivery": "by Monday", "estimatedDeliveryDelays": [],
 * "allowedProfileTypes": [ "PERSONAL", "BUSINESS" ], "payInProduct": "FAST",
 * "feePercentage": 0.0112, "estimatedDelivery": "2024-01-22T17:30:00Z",
 * "payOut": "BANK_TRANSFER", "disabled": false, "sourceAmount": 159.62,
 * "targetAmount": 200.50, "sourceCurrency": "GBP", "targetCurrency": "USD",
 * "fee": { "transferwise": 1.31, "payIn": 0.48, "discount": 0, "total": 1.79,
 * "priceSetId": 181, "partner": 0.0 }, "price": { "priceSetId": 181, "total": {
 * "type": "TOTAL", "label": "Total fees", "value": { "amount": 1.79,
 * "currency": "GBP", "label": "1.79 GBP" } }, "items": [ { "type":
 * "TRANSFERWISE", "label": "Our fee", "value": { "amount": 1.79, "currency":
 * "GBP", "label": "1.79 GBP" } } ] }, "payIn": "CREDIT" }, {
 * "formattedEstimatedDelivery": "by Monday", "estimatedDeliveryDelays": [],
 * "allowedProfileTypes": [ "PERSONAL", "BUSINESS" ], "payInProduct": "CHEAP",
 * "feePercentage": 0.0082, "estimatedDelivery": "2024-01-22T17:30:00Z",
 * "payOut": "BANK_TRANSFER", "disabled": false, "sourceAmount": 159.14,
 * "targetAmount": 200.50, "sourceCurrency": "GBP", "targetCurrency": "USD",
 * "fee": { "transferwise": 1.31, "payIn": 0.0, "discount": 0, "total": 1.31,
 * "priceSetId": 181, "partner": 0.0 }, "price": { "priceSetId": 181, "total": {
 * "type": "TOTAL", "label": "Total fees", "value": { "amount": 1.31,
 * "currency": "GBP", "label": "1.31 GBP" } }, "items": [ { "type":
 * "TRANSFERWISE", "label": "Our fee", "value": { "amount": 1.31, "currency":
 * "GBP", "label": "1.31 GBP" } } ] }, "payIn": "BANK_TRANSFER" }, {
 * "formattedEstimatedDelivery": "by Monday", "estimatedDeliveryDelays": [],
 * "allowedProfileTypes": [ "PERSONAL", "BUSINESS" ], "payInProduct": "CHEAP",
 * "feePercentage": 0.0082, "estimatedDelivery": "2024-01-22T17:30:00Z",
 * "payOut": "BANK_TRANSFER", "disabled": false, "sourceAmount": 159.14,
 * "targetAmount": 200.50, "sourceCurrency": "GBP", "targetCurrency": "USD",
 * "fee": { "transferwise": 1.31, "payIn": 0.0, "discount": 0, "total": 1.31,
 * "priceSetId": 181, "partner": 0.0 }, "price": { "priceSetId": 181, "total": {
 * "type": "TOTAL", "label": "Total fees", "value": { "amount": 1.31,
 * "currency": "GBP", "label": "1.31 GBP" } }, "items": [ { "type":
 * "TRANSFERWISE", "label": "Our fee", "value": { "amount": 1.31, "currency":
 * "GBP", "label": "1.31 GBP" } } ] }, "payIn": "PISP" }, {
 * "formattedEstimatedDelivery": "by Tuesday, January 23",
 * "estimatedDeliveryDelays": [], "allowedProfileTypes": [ "PERSONAL",
 * "BUSINESS" ], "payInProduct": "ADVANCED", "feePercentage": 0.0082,
 * "estimatedDelivery": "2024-01-23T17:30:00Z", "payOut": "BANK_TRANSFER",
 * "disabled": false, "sourceAmount": 159.14, "targetAmount": 200.50,
 * "sourceCurrency": "GBP", "targetCurrency": "USD", "fee": { "transferwise":
 * 1.31, "payIn": 0.0, "discount": 0, "total": 1.31, "priceSetId": 181,
 * "partner": 0.0 }, "price": { "priceSetId": 181, "total": { "type": "TOTAL",
 * "label": "Total fees", "value": { "amount": 1.31, "currency": "GBP", "label":
 * "1.31 GBP" } }, "items": [ { "type": "TRANSFERWISE", "label": "Our fee",
 * "value": { "amount": 1.31, "currency": "GBP", "label": "1.31 GBP" } } ] },
 * "payIn": "SWIFT" }, { "formattedEstimatedDelivery": "by Monday",
 * "estimatedDeliveryDelays": [], "allowedProfileTypes": [ "PERSONAL",
 * "BUSINESS" ], "payInProduct": "FAST", "feePercentage": 0.0112,
 * "estimatedDelivery": "2024-01-22T17:30:00Z", "payOut": "BANK_TRANSFER",
 * "disabled": false, "sourceAmount": 159.62, "targetAmount": 200.50,
 * "sourceCurrency": "GBP", "targetCurrency": "USD", "fee": { "transferwise":
 * 1.31, "payIn": 0.48, "discount": 0, "total": 1.79, "priceSetId": 181,
 * "partner": 0.0 }, "price": { "priceSetId": 181, "total": { "type": "TOTAL",
 * "label": "Total fees", "value": { "amount": 1.79, "currency": "GBP", "label":
 * "1.79 GBP" } }, "items": [ { "type": "TRANSFERWISE", "label": "Our fee",
 * "value": { "amount": 1.79, "currency": "GBP", "label": "1.79 GBP" } } ] },
 * "payIn": "VISA_DEBIT_OR_PREPAID" }, { "formattedEstimatedDelivery":
 * "by Monday", "estimatedDeliveryDelays": [], "allowedProfileTypes": [
 * "PERSONAL", "BUSINESS" ], "payInProduct": "FAST", "feePercentage": 0.0364,
 * "estimatedDelivery": "2024-01-22T17:30:00Z", "payOut": "BANK_TRANSFER",
 * "disabled": false, "sourceAmount": 163.79, "targetAmount": 200.50,
 * "sourceCurrency": "GBP", "targetCurrency": "USD", "fee": { "transferwise":
 * 1.31, "payIn": 4.65, "discount": 0, "total": 5.96, "priceSetId": 181,
 * "partner": 0.0 }, "price": { "priceSetId": 181, "total": { "type": "TOTAL",
 * "label": "Total fees", "value": { "amount": 5.96, "currency": "GBP", "label":
 * "5.96 GBP" } }, "items": [ { "type": "TRANSFERWISE", "label": "Our fee",
 * "value": { "amount": 5.96, "currency": "GBP", "label": "5.96 GBP" } } ] },
 * "payIn": "INTERNATIONAL_DEBIT" }, { "formattedEstimatedDelivery":
 * "by Monday", "estimatedDeliveryDelays": [], "allowedProfileTypes": [
 * "PERSONAL", "BUSINESS" ], "payInProduct": "FAST", "feePercentage": 0.0320,
 * "estimatedDelivery": "2024-01-22T17:30:00Z", "payOut": "BANK_TRANSFER",
 * "disabled": false, "sourceAmount": 163.04, "targetAmount": 200.50,
 * "sourceCurrency": "GBP", "targetCurrency": "USD", "fee": { "transferwise":
 * 1.31, "payIn": 3.9, "discount": 0, "total": 5.21, "priceSetId": 181,
 * "partner": 0.0 }, "price": { "priceSetId": 181, "total": { "type": "TOTAL",
 * "label": "Total fees", "value": { "amount": 5.21, "currency": "GBP", "label":
 * "5.21 GBP" } }, "items": [ { "type": "TRANSFERWISE", "label": "Our fee",
 * "value": { "amount": 5.21, "currency": "GBP", "label": "5.21 GBP" } } ] },
 * "payIn": "MC_BUSINESS_CREDIT" }, { "formattedEstimatedDelivery": "by Monday",
 * "estimatedDeliveryDelays": [], "allowedProfileTypes": [ "PERSONAL",
 * "BUSINESS" ], "payInProduct": "FAST", "feePercentage": 0.0112,
 * "estimatedDelivery": "2024-01-22T17:30:00Z", "payOut": "BANK_TRANSFER",
 * "disabled": false, "sourceAmount": 159.62, "targetAmount": 200.50,
 * "sourceCurrency": "GBP", "targetCurrency": "USD", "fee": { "transferwise":
 * 1.31, "payIn": 0.48, "discount": 0, "total": 1.79, "priceSetId": 181,
 * "partner": 0.0 }, "price": { "priceSetId": 181, "total": { "type": "TOTAL",
 * "label": "Total fees", "value": { "amount": 1.79, "currency": "GBP", "label":
 * "1.79 GBP" } }, "items": [ { "type": "TRANSFERWISE", "label": "Our fee",
 * "value": { "amount": 1.79, "currency": "GBP", "label": "1.79 GBP" } } ] },
 * "payIn": "MC_DEBIT_OR_PREPAID" }, { "formattedEstimatedDelivery":
 * "by Monday", "estimatedDeliveryDelays": [], "allowedProfileTypes": [
 * "PERSONAL", "BUSINESS" ], "payInProduct": "FAST", "feePercentage": 0.0112,
 * "estimatedDelivery": "2024-01-22T17:30:00Z", "payOut": "BANK_TRANSFER",
 * "disabled": false, "sourceAmount": 159.62, "targetAmount": 200.50,
 * "sourceCurrency": "GBP", "targetCurrency": "USD", "fee": { "transferwise":
 * 1.31, "payIn": 0.48, "discount": 0, "total": 1.79, "priceSetId": 181,
 * "partner": 0.0 }, "price": { "priceSetId": 181, "total": { "type": "TOTAL",
 * "label": "Total fees", "value": { "amount": 1.79, "currency": "GBP", "label":
 * "1.79 GBP" } }, "items": [ { "type": "TRANSFERWISE", "label": "Our fee",
 * "value": { "amount": 1.79, "currency": "GBP", "label": "1.79 GBP" } } ] },
 * "payIn": "MC_CREDIT" }, { "formattedEstimatedDelivery": "by Monday",
 * "estimatedDeliveryDelays": [], "allowedProfileTypes": [ "PERSONAL",
 * "BUSINESS" ], "payInProduct": "FAST", "feePercentage": 0.0112,
 * "estimatedDelivery": "2024-01-22T17:30:00Z", "payOut": "BANK_TRANSFER",
 * "disabled": false, "sourceAmount": 159.62, "targetAmount": 200.50,
 * "sourceCurrency": "GBP", "targetCurrency": "USD", "fee": { "transferwise":
 * 1.31, "payIn": 0.48, "discount": 0, "total": 1.79, "priceSetId": 181,
 * "partner": 0.0 }, "price": { "priceSetId": 181, "total": { "type": "TOTAL",
 * "label": "Total fees", "value": { "amount": 1.79, "currency": "GBP", "label":
 * "1.79 GBP" } }, "items": [ { "type": "TRANSFERWISE", "label": "Our fee",
 * "value": { "amount": 1.79, "currency": "GBP", "label": "1.79 GBP" } } ] },
 * "payIn": "CARD" } ], "notices": [], "transferFlowConfig": { "highAmount": {
 * "showFeePercentage": false, "trackAsHighAmountSender": false,
 * "showEducationStep": false, "offerPrefundingOption": false,
 * "overLimitThroughCs": false, "overLimitThroughWiseAccount": false } },
 * "rateTimestamp": "2024-01-20T06:45:14Z", "clientId":
 * "transferwise-personal-tokens", "expirationTime": "2024-01-20T07:16:06Z",
 * "id": "c99dd50c-d4dc-405c-81d4-a924dbf628ec", "type": "REGULAR",
 * "createdTime": "2024-01-20T06:46:06Z", "user": 6420559, "rateType": "FIXED",
 * "rateExpirationTime": "2024-01-22T08:59:59Z", "payOut": "BANK_TRANSFER",
 * "guaranteedTargetAmount": false, "providedAmountType": "TARGET", "funding":
 * "POST", "status": "PENDING", "rate": 1.27033, "sourceCurrency": "GBP",
 * "targetCurrency": "USD" }
 */

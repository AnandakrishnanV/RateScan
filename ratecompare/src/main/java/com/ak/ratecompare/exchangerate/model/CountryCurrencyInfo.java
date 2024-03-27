package com.ak.ratecompare.exchangerate.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "country_currency_info")
public class CountryCurrencyInfo {

    @JsonProperty("country")
    private String country;

    @JsonProperty("country_iso_2_char_code")
    private String countryIso2CharCode;

    @JsonProperty("country_iso_3_char_code")
    private String countryIso3CharCode;

    @Id
    @JsonProperty("un_m49")
    private int unM49;

    @JsonProperty("currency_iso_3_char_code")
    private String currencyIso3CharCode;

    @JsonProperty("currency_name")
    private String currencyName;

 
    public CountryCurrencyInfo() {
    }


	public CountryCurrencyInfo(String country, String countryIso2CharCode, String countryIso3CharCode, int unM49,
			String currencyIso3CharCode, String currencyName) {
		super();
		this.country = country;
		this.countryIso2CharCode = countryIso2CharCode;
		this.countryIso3CharCode = countryIso3CharCode;
		this.unM49 = unM49;
		this.currencyIso3CharCode = currencyIso3CharCode;
		this.currencyName = currencyName;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public String getCountryIso2CharCode() {
		return countryIso2CharCode;
	}


	public void setCountryIso2CharCode(String countryIso2CharCode) {
		this.countryIso2CharCode = countryIso2CharCode;
	}


	public String getCountryIso3CharCode() {
		return countryIso3CharCode;
	}


	public void setCountryIso3CharCode(String countryIso3CharCode) {
		this.countryIso3CharCode = countryIso3CharCode;
	}


	public int getUnM49() {
		return unM49;
	}


	public void setUnM49(int unM49) {
		this.unM49 = unM49;
	}


	public String getCurrencyIso3CharCode() {
		return currencyIso3CharCode;
	}


	public void setCurrencyIso3CharCode(String currencyIso3CharCode) {
		this.currencyIso3CharCode = currencyIso3CharCode;
	}


	public String getCurrencyName() {
		return currencyName;
	}


	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}


	@Override
	public String toString() {
		return "CountryCurrencyInfo [country=" + country + ", countryIso2CharCode=" + countryIso2CharCode
				+ ", countryIso3CharCode=" + countryIso3CharCode + ", unM49=" + unM49 + ", currencyIso3CharCode="
				+ currencyIso3CharCode + ", currencyName=" + currencyName + "]";
	}
    
}
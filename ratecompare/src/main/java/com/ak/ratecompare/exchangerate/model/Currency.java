package com.ak.ratecompare.exchangerate.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "currency")
public class Currency {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "currency_code", nullable = false, unique = true)
	private String currencyCode;

	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "symbol", nullable = false)
    private String symbol;

    @Column(name = "symbol_native", nullable = false)
    private String symbolNative;

    @Column(name = "decimal_digits", nullable = false)
    private Integer decimalDigits;

    @Column(name = "rounding", nullable = false)
    private Integer rounding;

    @Column(name = "name_plural", nullable = false)
    private String namePlural;

	public Currency() {
	}

	public Currency(Long id, String currencyCode, String name, String symbol, String symbolNative,
			Integer decimalDigits, Integer rounding, String namePlural) {
		super();
		this.id = id;
		this.currencyCode = currencyCode;
		this.name = name;
		this.symbol = symbol;
		this.symbolNative = symbolNative;
		this.decimalDigits = decimalDigits;
		this.rounding = rounding;
		this.namePlural = namePlural;
	}

	public Long getId() {
		return id;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getSymbolNative() {
		return symbolNative;
	}

	public void setSymbolNative(String symbolNative) {
		this.symbolNative = symbolNative;
	}

	public Integer getDecimalDigits() {
		return decimalDigits;
	}

	public void setDecimalDigits(Integer decimalDigits) {
		this.decimalDigits = decimalDigits;
	}

	public Integer getRounding() {
		return rounding;
	}

	public void setRounding(Integer rounding) {
		this.rounding = rounding;
	}

	public String getNamePlural() {
		return namePlural;
	}

	public void setNamePlural(String namePlural) {
		this.namePlural = namePlural;
	}

	@Override
	public String toString() {
		return "Currency [id=" + id + ", currencyCode=" + currencyCode + ", name=" + name + ", symbol=" + symbol
				+ ", symbolNative=" + symbolNative + ", decimalDigits=" + decimalDigits + ", rounding=" + rounding
				+ ", namePlural=" + namePlural + "]";
	}

}

package com.ak.ratecompare.exchangerate.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "providers")
public class Provider {
	
	@Id
	private String name;
	private String apiUrl;
	private String apiKey;
	
	private String currentToken;
	private LocalDateTime tokenExpiry;
	
	public Provider () {
		
	}
	
	public Provider(String name, String apiUrl, String apiKey, String currentToken, LocalDateTime tokenExpiry) {
		super();
		this.name = name;
		this.apiUrl = apiUrl;
		this.apiKey = apiKey;
		this.currentToken = currentToken;
		this.tokenExpiry = tokenExpiry;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getApiUrl() {
		return apiUrl;
	}

	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getCurrentToken() {
		return currentToken;
	}

	public void setCurrentToken(String currentToken) {
		this.currentToken = currentToken;
	}

	public LocalDateTime getTokenExpiry() {
		return tokenExpiry;
	}

	public void setTokenExpiry(LocalDateTime tokenExpiry) {
		this.tokenExpiry = tokenExpiry;
	}

	@Override
	public String toString() {
		return "Provider [name=" + name + ", apiUrl=" + apiUrl + ", apiKey=" + apiKey + ", currentToken=" + currentToken
				+ ", tokenExpiry=" + tokenExpiry + "]";
	}
	
	public boolean isTokenExpired() {
		return LocalDateTime.now().isAfter(tokenExpiry);
	}

}

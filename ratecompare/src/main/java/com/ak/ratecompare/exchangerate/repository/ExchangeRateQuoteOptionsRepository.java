package com.ak.ratecompare.exchangerate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ak.ratecompare.exchangerate.model.ExchangeRate;

public interface ExchangeRateQuoteOptionsRepository extends JpaRepository<ExchangeRate, Long> {

}

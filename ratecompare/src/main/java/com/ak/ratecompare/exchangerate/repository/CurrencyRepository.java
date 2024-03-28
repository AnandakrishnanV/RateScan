package com.ak.ratecompare.exchangerate.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ak.ratecompare.exchangerate.model.Currency;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
	Optional<Currency> findByCurrencyCode(String code);

}

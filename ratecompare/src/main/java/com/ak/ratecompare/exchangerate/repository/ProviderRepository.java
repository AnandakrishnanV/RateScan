package com.ak.ratecompare.exchangerate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ak.ratecompare.exchangerate.model.Provider;

public interface ProviderRepository extends JpaRepository<Provider, String> {
    // Additional methods if needed
}

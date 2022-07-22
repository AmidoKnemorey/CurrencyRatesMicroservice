package com.example.supplyservice.repository;

import com.example.currencyrates.bankservice.model.CurrencyDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//TODO HANDLE THE MESS HERE
public interface CurrencyDayRepository extends JpaRepository<CurrencyDay, String> {
    @Query("SELECT c FROM currency_days c WHERE c.effectiveDate = ?1")
    CurrencyDay findTableByDate (String requiredDate);
}
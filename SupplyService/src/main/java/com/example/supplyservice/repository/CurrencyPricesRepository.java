package com.example.supplyservice.repository;

import com.example.supplyservice.model.BankCurrencyUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CurrencyPricesRepository extends JpaRepository<BankCurrencyUnit, Long> {
    @Query("SELECT b FROM bank_currency_units b WHERE b.actualTradingDay.id = ?1")
    List<BankCurrencyUnit> findCurrencyUnitsByCertainDayId (Long tradingDayId);
}
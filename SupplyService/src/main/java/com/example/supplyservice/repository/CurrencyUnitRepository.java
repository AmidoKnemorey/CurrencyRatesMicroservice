package com.example.supplyservice.repository;

import com.example.currencyrates.bankservice.model.CurrencyUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CurrencyUnitRepository extends JpaRepository<CurrencyUnit, Long> {
    @Query("SELECT c FROM currency_units c WHERE c.currencyDay = ?1")
    List<CurrencyUnit> findCurrencyUnitsByCertainDayId (Long instructorId);
}

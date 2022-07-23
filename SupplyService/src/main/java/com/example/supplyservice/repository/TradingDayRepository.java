package com.example.supplyservice.repository;

import com.example.supplyservice.model.TradingDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TradingDayRepository extends JpaRepository<TradingDay, String> {
    @Query("SELECT t FROM trading_days t WHERE t.effectiveDate = ?1")
    TradingDay findTableByDate (String requiredDate);
}
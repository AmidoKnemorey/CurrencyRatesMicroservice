package com.example.supplyservice.service;

import com.example.supplyservice.model.TradingDay;
import com.example.supplyservice.repository.TradingDayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TradingDayService {

    private final TradingDayRepository tradingDayRepository;

    @Autowired
    public TradingDayService(TradingDayRepository tradingDayRepository) {
        this.tradingDayRepository = tradingDayRepository;
    }

    public void saveCurrencyDay(TradingDay tradingDay) {
        this.tradingDayRepository.save(tradingDay);
    }

    public Optional<TradingDay> getTradingDayByDate(String date) {
        return Optional.ofNullable(this.tradingDayRepository.findTableByDate(date));
    }
}

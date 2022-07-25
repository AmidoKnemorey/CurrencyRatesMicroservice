package com.example.supplyservice.service;

import com.example.supplyservice.model.BankCurrencyUnit;
import com.example.supplyservice.model.TradingDay;
import com.example.supplyservice.repository.CurrencyPricesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyPricesService {

    private final CurrencyPricesRepository currencyPricesRepository;

    @Autowired
    public CurrencyPricesService(CurrencyPricesRepository currencyPricesRepository) {
        this.currencyPricesRepository = currencyPricesRepository;
    }

    public List<BankCurrencyUnit> getCurrencyUnitsByCertainDayId(Long dayId) {
        return this.currencyPricesRepository.findCurrencyUnitsByCertainDayId(dayId);
    }

    public void saveCurrencyUnits(List<BankCurrencyUnit> currencyUnitsSet, TradingDay tradingDay) {
        for (BankCurrencyUnit currencyUnit : currencyUnitsSet) {
            saveOneUnit(BankCurrencyUnit.builder()
                    .currency(currencyUnit.getCurrency())
                    .code(currencyUnit.getCode())
                    .bid(currencyUnit.getBid())
                    .ask(currencyUnit.getAsk())
                    .actualTradingDay(tradingDay)
                    .build());
        }
    }

    private void saveOneUnit(BankCurrencyUnit bankCurrencyUnit) {
        this.currencyPricesRepository.save(bankCurrencyUnit);
    }
}
package com.example.supplyservice.service;

import com.example.supplyservice.model.BankCurrencyUnit;
import com.example.supplyservice.model.TradingDay;
import com.example.supplyservice.repository.CurrencyUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CurrencyUnitService {

    private final CurrencyUnitRepository currencyUnitRepository;

    @Autowired
    public CurrencyUnitService(CurrencyUnitRepository currencyUnitRepository) {
        this.currencyUnitRepository = currencyUnitRepository;
    }

    public List<BankCurrencyUnit> getCurrencyUnitsByCertainDayId(Long dayId) {
        return this.currencyUnitRepository.findCurrencyUnitsByCertainDayId(dayId);
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
        this.currencyUnitRepository.save(bankCurrencyUnit);
    }
}
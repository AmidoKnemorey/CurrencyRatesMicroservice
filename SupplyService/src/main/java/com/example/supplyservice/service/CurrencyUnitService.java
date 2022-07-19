package com.example.supplyservice.service;

import com.example.currencyrates.bankservice.model.CurrencyDay;
import com.example.currencyrates.bankservice.model.CurrencyUnit;
import com.example.currencyrates.clientservice.repository.CurrencyUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CurrencyUnitService {
    //TODO RESTORE THIS CLASS

    private final CurrencyUnitRepository currencyUnitRepository;

    @Autowired
    public CurrencyUnitService(CurrencyUnitRepository currencyUnitRepository) {
        this.currencyUnitRepository = currencyUnitRepository;
    }

    public List<CurrencyUnit> getCurrencyUnitsByCertainDayId(Long id) {
        return this.currencyUnitRepository.findCurrencyUnitsByCertainDayId(id);
    }

    public void saveCurrencyUnits(Set<CurrencyUnit> currencyUnitsSet, CurrencyDay currencyDay) {
        for (CurrencyUnit currencyUnit : currencyUnitsSet) {
            saveOneUnit(CurrencyUnit.builder()
                    .currency(currencyUnit.getCurrency())
                    .code(currencyUnit.getCode())
                    .bid(currencyUnit.getBid())
                    .ask(currencyUnit.getAsk())
                    .currencyDay(currencyDay)
                    .build());
        }
    }

    private void saveOneUnit(CurrencyUnit currencyUnit) {
        this.currencyUnitRepository.save(currencyUnit);
    }


}

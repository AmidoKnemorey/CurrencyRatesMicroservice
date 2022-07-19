package com.example.supplyservice.service;

import com.example.currencyrates.bankservice.model.CurrencyDay;
import com.example.currencyrates.clientservice.repository.CurrencyDayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CurrencyDayService {

    //TODO RESTORE THIS CLASS

    private final CurrencyDayRepository currencyDayRepository;

    @Autowired
    public CurrencyDayService(CurrencyDayRepository currencyDayRepository) {
        this.currencyDayRepository = currencyDayRepository;
    }


    public void saveCurrencyDay(CurrencyDay currencyDay) {
        this.currencyDayRepository.save(currencyDay);
    }

    public Optional<CurrencyDay> getTableByDate(String date) {
        return Optional.ofNullable(this.currencyDayRepository.findTableByDate(date));
    }
}

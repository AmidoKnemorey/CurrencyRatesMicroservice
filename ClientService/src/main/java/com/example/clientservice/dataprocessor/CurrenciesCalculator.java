package com.example.clientservice.dataprocessor;

import com.example.clientservice.model.ClientExchangeRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;

public class CurrenciesCalculator {

    public static ResponseEntity<BigDecimal> calculatePrice(ClientExchangeRequest clientExchangeRequest, HashMap<String, BigDecimal> currenciesPrices) {

        return new ResponseEntity<>(Arrays.stream(clientExchangeRequest.getForeignCurrencies()).map(currencyToExchange ->
                        currenciesPrices.get(currencyToExchange.getCurrencyCode())
                                .multiply(currencyToExchange.getCurrencyAmount()))
                .reduce(BigDecimal.ZERO, (a, b) -> a.add(b)), HttpStatus.OK);
    }
}
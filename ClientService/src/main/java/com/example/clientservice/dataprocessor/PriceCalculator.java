package com.example.clientservice.dataprocessor;

import com.example.clientservice.model.ClientCurrencyUnit;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public class PriceCalculator {
    public static BigDecimal computeTotalPrice(List<ClientCurrencyUnit> currenciesToBuy, HashMap<String, BigDecimal> prices) {
        return currenciesToBuy.stream()
                .map(curr -> curr.getCurrencyAmount().multiply(prices.get(curr.getCurrencyCode())))
                .reduce(new BigDecimal("0.0"), BigDecimal::add);
    }
}
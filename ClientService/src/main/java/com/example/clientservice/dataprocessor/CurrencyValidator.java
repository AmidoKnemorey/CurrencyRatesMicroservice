package com.example.clientservice.dataprocessor;

import com.example.clientservice.model.ClientCurrencyUnit;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CurrencyValidator {

    private static final List<String> ALL_POSSIBLE_CURRENCIES =
            List.of("USD", "AUD", "CAD", "EUR", "HUF", "CHF", "GBP", "JPY", "CZK", "DKK", "NOK","SEK", "XDR");

    public boolean validateRequest(List<ClientCurrencyUnit> clientForeignCurrencies) {
        return currenciesTypesValidator(clientForeignCurrencies) && currenciesValuesValidator(clientForeignCurrencies);
    }

    private static boolean currenciesTypesValidator(List<ClientCurrencyUnit> clientForeignCurrencies) {
        if (ALL_POSSIBLE_CURRENCIES.containsAll(clientForeignCurrencies.stream()
                .map(ClientCurrencyUnit::getCurrencyCode)
                .collect(Collectors.toList()))) {
            return true;
        } else {
            throw new HttpStatusCodeException(HttpStatus.BAD_REQUEST, "One currency from your request isn't supported") {
                @Override
                public HttpStatus getStatusCode() {
                    return super.getStatusCode();
                }
            };
        }
    }

    private static boolean currenciesValuesValidator(List<ClientCurrencyUnit> clientForeignCurrencies) {
        return clientForeignCurrencies.stream().
                map(ClientCurrencyUnit::getCurrencyAmount)
                .anyMatch(each -> each.compareTo(BigDecimal.ZERO) <= 0);
    }
}
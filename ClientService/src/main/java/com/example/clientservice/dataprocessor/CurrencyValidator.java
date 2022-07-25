package com.example.clientservice.dataprocessor;

import com.example.clientservice.model.ClientCurrencyUnit;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class CurrencyValidator {

    private static final List<String> ALL_POSSIBLE_CURRENCIES =
            List.of("USD", "AUD", "CAD", "EUR", "HUF", "CHF", "GBP", "JPY", "CZK", "DKK", "NOK", "SEK", "XDR");

    public static ResponseEntity<String> validateRequest(List<ClientCurrencyUnit> clientForeignCurrencies) {

        ResponseEntity<String> firstValidationResult = currenciesTypesValidator(clientForeignCurrencies);
        ResponseEntity<String> secondValidationResult = currenciesValuesValidator(clientForeignCurrencies);

        if (firstValidationResult.getStatusCode().value() != 200) {
            return firstValidationResult;
        } else if (secondValidationResult.getStatusCode().value() != 200) {
            return secondValidationResult;
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    private static ResponseEntity<String> currenciesTypesValidator(List<ClientCurrencyUnit> clientForeignCurrencies) {
        if (ALL_POSSIBLE_CURRENCIES.containsAll(clientForeignCurrencies.stream()
                .map(ClientCurrencyUnit::getCurrencyCode)
                .collect(Collectors.toList()))) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("One currency from your request isn't supported", HttpStatus.BAD_REQUEST);
        }
    }

    private static ResponseEntity<String> currenciesValuesValidator(List<ClientCurrencyUnit> clientForeignCurrencies) {
        return clientForeignCurrencies.stream().
                map(ClientCurrencyUnit::getCurrencyAmount)
                .noneMatch(each -> each.compareTo(BigDecimal.ZERO) <= 0) ?
                new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>("One value is equal or less then zero", HttpStatus.BAD_REQUEST);
    }
}
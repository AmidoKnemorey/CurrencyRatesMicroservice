package com.example.clientservice.controller;

import com.example.clientservice.dataprocessor.CurrencyValidator;
import com.example.clientservice.dataprocessor.CurrenciesCalculator;
import com.example.clientservice.model.ClientExchangeRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@RestController
@RequestMapping("/api/currencies/")
public class ClientRequestsController {

    @PostMapping("/buy")
    public ResponseEntity<String> countPriceOfCurrencies(@RequestBody ClientExchangeRequest clientExchangeRequest) {
        ResponseEntity<String> resultOfValidation = CurrencyValidator.validateRequest(List.of(clientExchangeRequest.getForeignCurrencies()));
        if (resultOfValidation.getStatusCode().value() == 200) {
            HashMap<String, BigDecimal> currenciesPrices = getCurrenciesPrices(clientExchangeRequest.getCertainDate());
            return currenciesPrices.isEmpty() ?
                    new ResponseEntity<>("there are no exchange rates for requested day", HttpStatus.BAD_REQUEST)
                    : new ResponseEntity<>(CurrenciesCalculator.calculatePrice(clientExchangeRequest, currenciesPrices).toString(), HttpStatus.OK);
        } else {
            return resultOfValidation;
        }
    }

    private HashMap<String, BigDecimal> getCurrenciesPrices(String date) {
        try {
            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8082/api/supply/currencyRates/" + date))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

            return new ObjectMapper().readValue(getResponse.body(), new TypeReference<>() {});

        } catch (URISyntaxException | IOException | InterruptedException exception) {
            System.err.println("Something went wrong in getCurrenciesPrices method in ClientRequestController class.");
            return new HashMap<>();
        }
    }
}
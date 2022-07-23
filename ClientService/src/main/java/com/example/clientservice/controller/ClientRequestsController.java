package com.example.clientservice.controller;

import com.example.clientservice.dataprocessor.CurrencyValidator;
import com.example.clientservice.dataprocessor.CurrenciesCalculator;
import com.example.clientservice.model.ClientCurrencyUnit;
import com.example.clientservice.model.ClientExchangeRequest;
import com.google.gson.Gson;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/currencies/")
public class ClientRequestsController {

//    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/buy")
    public ResponseEntity<String> countPriceOfCurrencies(@RequestBody ClientExchangeRequest clientExchangeRequest) {

        if (CurrencyValidator.validateRequest(List.of(clientExchangeRequest.getForeignCurrencies()))) {
            HashMap<String,BigDecimal> currenciesPrices = getCurrenciesPrices(clientExchangeRequest.getCertainDate());
            return new ResponseEntity<>(CurrenciesCalculator.calculatePrice(clientExchangeRequest, currenciesPrices).toString(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("bad request", HttpStatus.BAD_REQUEST);
        }
    }

    private HashMap<String, BigDecimal> getCurrenciesPrices(String date) {
        try {
            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/api/supply/currencyRatesCertainDate/" + date))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
            Gson gson = new Gson();
            gson.toJson(getResponse.body());
            System.out.println(gson.fromJson(getResponse.body(), Object.class));
        } catch (URISyntaxException | IOException | InterruptedException exception) {
            System.err.println("Something went wrong in getCurrenciesPrices method in ClientRequestController class.");
        }
        return new HashMap<>();
    }
}
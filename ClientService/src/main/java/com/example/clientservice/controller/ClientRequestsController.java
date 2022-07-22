package com.example.clientservice.controller;

import com.example.clientservice.dataprocessor.CurrencyValidator;
import com.example.clientservice.dataprocessor.JsonValidator;
import com.example.clientservice.dataprocessor.CurrenciesCalculator;
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
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/currencies/")
public class ClientRequestsController {

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/buy")
    public ResponseEntity<String> countPriceOfCurrencies(@RequestBody ClientExchangeRequest clientExchangeRequest) {

        if (!CurrencyValidator.validateRequest(List.of(clientExchangeRequest.getClientForeignCurrencies()))) {

            System.out.println(JsonValidator.isJSONValid(clientExchangeRequest));

            return new ResponseEntity<>(new BigDecimal("0.0").toString(), HttpStatus.BAD_REQUEST);
        } else {
            HashMap<String,BigDecimal> currenciesPrices = getCurrenciesPrices(clientExchangeRequest.getCertainDate());
            return new ResponseEntity<>(CurrenciesCalculator.handleTheRequest(clientExchangeRequest, currenciesPrices).toString(), HttpStatus.OK);
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

        } catch (URISyntaxException | IOException | InterruptedException exception) {
            System.err.println("Something went wrong in getCurrenciesPrices method in ClientRequestController class.");
        }
        return new HashMap<>();
    }
}
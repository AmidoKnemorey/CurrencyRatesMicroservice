package com.example.clientservice.controller;

import com.example.clientservice.dataprocessor.CurrencyValidator;
import com.example.clientservice.dataprocessor.PriceCalculator;
import com.example.clientservice.model.BankCurrencyUnit;
import com.example.clientservice.model.ClientExchangeRequest;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final PriceCalculator priceCalculator;
    private final CurrencyValidator currencyValidator;
    private final CurrencyDayService currencyDayService;
    private final CurrencyUnitService currencyUnitService;

    @Autowired
    public ClientRequestsController(PriceCalculator priceCalculator, CurrencyValidator currencyValidator, CurrencyDayService currencyDayService, CurrencyUnitService currencyUnitService) {
        this.priceCalculator = priceCalculator;
        this.currencyValidator = currencyValidator;
        this.currencyDayService = currencyDayService;
        this.currencyUnitService = currencyUnitService;
    }


    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/buy")
    public ResponseEntity<BigDecimal> countPriceOfCurrencies(@RequestBody ClientExchangeRequest clientExchangeRequest) throws URISyntaxException, IOException, InterruptedException {

        if (!this.currencyValidator.validateRequest(List.of(clientExchangeRequest.getClientForeignCurrencies()))) {
            return new ResponseEntity<>(new BigDecimal("0.0"), HttpStatus.BAD_REQUEST);
        } else {
            HashMap<String,BigDecimal> currenciesPrices = getCurrenciesPrices(clientExchangeRequest.getCertainDate());
            //TODO NEXT
        }
        // 2: проверить работу метода validateCurrencies в insomnia
    }



    //TODO NEXT METHOD
    // 1: проверить нижний метод
    private HashMap<String, BigDecimal> getCurrenciesPrices(String date) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/api/supply/currencyRatesCertainDate/" + date))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
        Gson gson = new Gson();
        gson.toJson(getResponse.body());
        return new HashMap<>();
    }
}
package com.example.supplyservice.controller;

import com.example.supplyservice.model.TradingDay;
import com.example.supplyservice.service.CurrencyPricesService;
import com.example.supplyservice.service.TradingDayService;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/api/supply/")
public class SupplyController {

    private final CurrencyPricesService currencyPricesService;
    private final TradingDayService tradingDayService;

    public SupplyController(CurrencyPricesService currencyPricesService, TradingDayService tradingDayService) {
        this.currencyPricesService = currencyPricesService;
        this.tradingDayService = tradingDayService;
    }

    @GetMapping("/currencyRates/{interestedTradeDay}")
    public ResponseEntity<String> getCurrencyRatesForCertainDate(@PathVariable String interestedTradeDay) {
        HashMap<String, BigDecimal> resultCurrenciesPricesPairsSet = new HashMap<>();
        Gson gson = new Gson();
        Optional<TradingDay> requestedTradingDay = this.tradingDayService.getTradingDayByDate(interestedTradeDay);
        if (requestedTradingDay.isPresent()) {
            this.currencyPricesService.getCurrencyUnitsByCertainDayId(requestedTradingDay.get().getId())
                    .forEach(e -> resultCurrenciesPricesPairsSet.put(e.getCode(), e.getAsk()));
            return new ResponseEntity<>(gson.toJson(resultCurrenciesPricesPairsSet), HttpStatus.OK);
        } else {
            return getCurrencyRatesFromBankServer(interestedTradeDay);
        }
    }

    private ResponseEntity<String> getCurrencyRatesFromBankServer(String interestedTradeDay) {
        try {
            Gson gson = new Gson();
            HashMap<String, BigDecimal> resultCurrenciesPricesPairsSet = new HashMap<>();
            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(new URI("https://api.nbp.pl/api/exchangerates/tables/C/" + interestedTradeDay + "/"))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

            TradingDay[] actualTradingDays = getResponse.statusCode() != 200 ? null
                    : gson.fromJson(getResponse.body(), TradingDay[].class);

            if (actualTradingDays != null) {
                actualTradingDays[0].getRates().forEach(pair -> resultCurrenciesPricesPairsSet.put(pair.getCode(), pair.getAsk()));
                this.tradingDayService.saveCurrencyDay(actualTradingDays[0]);
                this.currencyPricesService.saveCurrencyUnits(actualTradingDays[0].getRates(), actualTradingDays[0]);
            } else {

            }
            return new ResponseEntity<>(gson.toJson(resultCurrenciesPricesPairsSet), HttpStatus.OK);

        } catch (URISyntaxException | IOException | InterruptedException exception) {
            System.err.println("Something went wrong in getCurrencyRatesForCertainDate method");
            return new ResponseEntity<>("error within a getCurrencyRatesForCertainDate method.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
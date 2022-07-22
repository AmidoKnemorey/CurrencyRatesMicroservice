package com.example.supplyservice.controller;

import com.example.supplyservice.model.ActualTradingDay;
import com.google.gson.Gson;
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

@RestController
@RequestMapping("/api/supply/")
public class SupplyController {
    @GetMapping("/currencyRatesCertainDate/{certainDate}")
    public HashMap<String, BigDecimal> getCurrencyRatesForCertainDate(@PathVariable String certainDate) {
        HashMap<String, BigDecimal> resultCurrenciesPricesPairsSet = new HashMap<>();
        try {

            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(new URI("https://api.nbp.pl/api/exchangerates/tables/C/" + certainDate + "/"))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();

            ActualTradingDay[] actualTradingDays = getResponse.statusCode() != 200 ? null
                    : gson.fromJson(getResponse.body(), ActualTradingDay[].class);

            if (actualTradingDays != null) {
                actualTradingDays[0].getRates().forEach(pair -> resultCurrenciesPricesPairsSet.put(pair.getCode(), pair.getAsk()));
            }
            return resultCurrenciesPricesPairsSet;

        } catch (URISyntaxException | IOException |InterruptedException exception) {
            System.err.println("Something went wrong in getCurrencyRatesForCertainDate method");
            return resultCurrenciesPricesPairsSet;
        }
    }
}




//    @GetMapping("/currencyRatesCertainDate/{certainDate}")
//    public List<CurrencyUnit> getCurrencyRatesForCertainDate(@PathVariable String certainDate) {
//
//        try {
//
//            HttpRequest getRequest = HttpRequest.newBuilder()
//                    .uri(new URI("https://api.nbp.pl/api/exchangerates/tables/C/" + certainDate + "/"))
//                    .header("Accept", "application/json")
//                    .GET()
//                    .build();
//
//            HttpClient httpClient = HttpClient.newHttpClient();
//            HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
//
//            Gson gson = new Gson();
//
//            CurrencyDay[] currencyDayByBankAPI = getResponse.statusCode() != 200 ? null
//                    : gson.fromJson(getResponse.body(), CurrencyDay[].class);
//
//            if (currencyDayByBankAPI != null) {
//
//                return currencyDayByBankAPI[0].getAllCurrencyUnits();
//            } else {
//                return new ArrayList<>();
//            }
//
//        } catch (URISyntaxException | IOException |InterruptedException exception) {
//            System.err.println("Something went wrong in getCurrencyRatesForCertainDate method");
//            return new ArrayList<>();
//        }
//    }
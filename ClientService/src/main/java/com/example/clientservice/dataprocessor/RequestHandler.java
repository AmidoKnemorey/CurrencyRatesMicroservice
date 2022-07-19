package com.example.clientservice.dataprocessor;

import com.example.clientservice.model.ClientExchangeRequest;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

@Component
public class RequestHandler {

    public RequestHandler()


    public BigDecimal handleTheRequest(ClientExchangeRequest clientExchangeRequest) {
        try {
            String currencyDay = clientExchangeRequest.getCertainDate();

            allCurrencyUnitsForCertainDay = this.currencyUnitService.getCurrencyUnitsByCertainDayId(currencyDay.get().getId());
            allForeignCurrencyFromRequest = Arrays.stream(clientExchangeRequest.getForeignCurrencies())
                    .collect(Collectors.toList());


            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();

            allCurrencyUnitsForCertainDay = gson.fromJson(getResponse.body(), CurrencyDay.class).getAllCurrencyUnits();
            allForeignCurrencyFromRequest = Arrays.stream(clientExchangeRequest.getForeignCurrencies())
                    .collect(Collectors.toList());

            System.out.println(getResponse.body());

            CurrencyDay currencyDayByBankAPI = gson.fromJson(getResponse.body(), CurrencyDay.class);
            System.out.println(currencyDayByBankAPI);

            if (currencyDayByBankAPI != null) {
                this.currencyDayService.saveCurrencyDay(currencyDayByBankAPI);
                this.currencyUnitService.saveCurrencyUnits(new HashSet<>(allCurrencyUnitsForCertainDay), currencyDayByBankAPI);
            }

        } catch (URISyntaxException | IOException | InterruptedException exception) {
            System.err.println("Something went wrong in getThePriceOfCertainCurrenciesAmount method");
            return new BigDecimal("0.0");
        }
        return new BigDecimal("0.0");
    }
}

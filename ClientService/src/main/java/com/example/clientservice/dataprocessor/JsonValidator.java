package com.example.clientservice.dataprocessor;

import com.example.clientservice.model.ClientExchangeRequest;
import com.google.gson.Gson;

public class JsonValidator {
    public static boolean isJSONValid(ClientExchangeRequest clientExchangeRequest) {
        Gson gson = new Gson();
        try {
            gson.fromJson(gson.toJson(clientExchangeRequest), ClientExchangeRequest.class);
            return true;
        } catch(com.google.gson.JsonSyntaxException ex) {
            return false;
        }
    }
}

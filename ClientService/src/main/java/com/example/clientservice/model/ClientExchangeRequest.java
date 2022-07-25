package com.example.clientservice.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embedded;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClientExchangeRequest {

    @SerializedName("certainDate")
    private String certainDate;

    @Embedded
    @SerializedName("foreignCurrencies")
    private ClientCurrencyUnit[] foreignCurrencies;
}
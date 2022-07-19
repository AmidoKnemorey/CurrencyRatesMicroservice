package com.example.clientservice.model;

import com.google.gson.annotations.SerializedName;
import lombok.*;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Getter
@Setter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class ClientCurrencyUnit {

    @SerializedName("currencyCode")
    private String currencyCode;

    @SerializedName("currencyAmount")
    private BigDecimal currencyAmount;

    @Override
    public String toString() {
        return "ForeignCurrency{" +
                "currencyCode='" + currencyCode + '\'' +
                ", currencyAmount=" + currencyAmount +
                '}';
    }

}
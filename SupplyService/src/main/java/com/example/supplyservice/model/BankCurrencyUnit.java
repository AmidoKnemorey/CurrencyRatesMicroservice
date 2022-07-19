package com.example.supplyservice.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "bank_currency_units")
public class BankCurrencyUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @SerializedName("currency")
    private String currency;
    @SerializedName("code")
    private String code;
    @SerializedName("bid")
    private BigDecimal bid;
    @SerializedName("ask")
    private BigDecimal ask;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "currencyDay_id")
    private CurrencyDay currencyDay;

    @Override
    public String toString() {
        return "CurrencyUnit{" +
                "id=" + id +
                ", currency='" + currency + '\'' +
                ", code='" + code + '\'' +
                ", bid=" + bid +
                ", ask=" + ask +
                ", currencyDay=" + currencyDay +
                '}';
    }
}

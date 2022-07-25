package com.example.supplyservice.model;

import com.google.gson.annotations.SerializedName;
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
    @Column(length = 30)
    private String currency;
    @SerializedName("code")
    @Column(length = 10)
    private String code ;
    @SerializedName("bid")
    @Column(length = 10, nullable = false, updatable = false, precision = 8, scale = 6)
    private BigDecimal bid;
    @SerializedName("ask")
    @Column(length = 10, nullable = false, updatable = false, precision = 8, scale = 6)
    private BigDecimal ask;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "currency_day_id")
    private TradingDay actualTradingDay;
}
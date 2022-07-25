package com.example.supplyservice.model;


import com.google.gson.annotations.SerializedName;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "trading_days")
public class TradingDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "currency_day_id")
    private Long id;

    @SerializedName("table")
    @Column(length = 5)
    private String table;

    @SerializedName("no")
    @Column(length = 30)
    private String no;

    @SerializedName("tradingDate")
    @Column(length = 10)
    private String tradingDate;

    @SerializedName("effectiveDate")
    @Column(length = 10)
    private String effectiveDate;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH, mappedBy = "actualTradingDay")
    @SerializedName("rates")
    private List<BankCurrencyUnit> rates = new ArrayList<>();
}
package com.example.supplyservice.model;


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
public class ActualTradingDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "currency_day_id")
    private Long id;

    @SerializedName("table")
    private String table;

    @SerializedName("no")
    private String no;

    @SerializedName("tradingDate")
    private String tradingDate;

    @SerializedName("effectiveDate")
    private String effectiveDate;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH, mappedBy = "currencyDay")
    @SerializedName("rates")
    private List<CurrencyUnit> allCurrencyUnits = new ArrayList<>();

    @Override
    public String toString() {
        return "CurrencyDay{" +
                "id=" + id +
                ", table='" + table + '\'' +
                ", no='" + no + '\'' +
                ", tradingDate='" + tradingDate + '\'' +
                ", effectiveDate='" + effectiveDate + '\'' +
                ", allCurrencyUnits=" + allCurrencyUnits +
                '}';
    }
}

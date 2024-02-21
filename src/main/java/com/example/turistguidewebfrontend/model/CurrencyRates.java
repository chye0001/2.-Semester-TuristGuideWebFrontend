package com.example.turistguidewebfrontend.model;

import java.util.Date;
import java.util.List;

public class CurrencyRates {
    private String table;
    private Rate rates;
    private Date lastupdate;

    private final List<String> currencyList = List.of("DKK", "EUR", "USD");

    public CurrencyRates(String table, Rate rates, Date lastupdate) {
        this.table = table;
        this.rates = rates;
        this.lastupdate = lastupdate;
    }
    public CurrencyRates(){}
    public double getDKK(){
        return rates.getDKK();
    }
    public double getUSD(){
        return rates.getUSD();
    }
    public double getEUR(){
        return rates.getEUR();
    }

    public List<String> getCurrencyList() {
        return currencyList;
    }

    @Override
    public String toString() {
        return "Valutakurser hentet fra CDN skyen {" +
                "table: '" + table + '\'' +
                ", rates: {" + rates + '}' +
                ", lastupdate: " + lastupdate +
                '}';
    }

    public static class Rate {
        private double DKK;
        private double EUR;
        private double USD;

        public Rate(){
        }
        public double getDKK() {
            return DKK;
        }
        public double getEUR() {
            return EUR;
        }
        public double getUSD() {
            return USD;
        }

        @Override
        public String toString() {
            return "DKK: " + DKK + ", " +
                    "EUR: " + EUR + ", "+
                    "USD: " + USD;
        }
    }
}

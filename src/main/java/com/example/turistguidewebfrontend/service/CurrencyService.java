package com.example.turistguidewebfrontend.service;

import com.example.turistguidewebfrontend.model.CurrencyRates;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class CurrencyService {

    public CurrencyRates getRates() throws IOException {
        //Se evt. mere her: https://forexvalutaomregner.dk/pages/api);
        URL url = new URL("https://cdn.forexvalutaomregner.dk/api/latest.json");

        // Indlæsning af valutakurser
        BufferedReader inputFromUrl = new BufferedReader(new InputStreamReader(url.openStream()));

        //Mapning af JSON data til Java objekt vha. Gson
        // Husk dependency i pom.xml og import i denne klasse
        CurrencyRates currencyRates = new Gson().fromJson(inputFromUrl, CurrencyRates.class);
        //Close stream
        inputFromUrl.close();
        return currencyRates;
    }
}

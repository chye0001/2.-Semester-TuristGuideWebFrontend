package com.example.turistguidewebfrontend.service;

import com.example.turistguidewebfrontend.model.CurrencyRates;
import com.example.turistguidewebfrontend.model.TouristAttraction;
import com.example.turistguidewebfrontend.repository.TouristRepository;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

@Service
public class CurrencyService {

    private final TouristRepository repository;
    double dkkRate = getRates().getDKK();
    double usdRate = getRates().getUSD();
    double eurRate = getRates().getEUR();
    public CurrencyService(TouristRepository repository) throws IOException {
        this.repository = repository;
    }

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
        System.out.println(currencyRates);
        return currencyRates;
    }

    public List<String> getAllCurrencies() {
        CurrencyRates currencyRates = new CurrencyRates();
        List<String> rates = currencyRates.getCurrencyList();
        return rates;
    }

    public void getCurrencyRate(String currency) {
        List<TouristAttraction> attractionList = repository.viewAll();

        switch (currency) {
            case "DKK" -> convertCurrencyToDKK(attractionList);
            case "USD" -> convertCurrencyToUSD(attractionList);
            case "EUR" -> convertCurrencyToEUR(attractionList);
        }
    }

    private void convertCurrencyToDKK(List<TouristAttraction> attractionList) {
        double DKKUSD = dkkRate / usdRate;
        double DKKEUR = dkkRate / eurRate;

        for (TouristAttraction attraction : attractionList) {
            String previousCurrency = attraction.getCurrency();
            double price = attraction.getPrice();

            if (previousCurrency.equalsIgnoreCase("USD")) {
                attraction.setPrice(price * DKKUSD);
            }

            if (previousCurrency.equalsIgnoreCase("EUR")) {
                attraction.setPrice(price * DKKEUR);
            }
        }
    }

    private void convertCurrencyToUSD(List<TouristAttraction> attractionList){
        double USDDKK = usdRate / dkkRate;
        double USDEUR = usdRate / eurRate;

        for (TouristAttraction attraction:attractionList) {
            String previousCurrency = attraction.getCurrency();
            double price = attraction.getPrice();

            if (previousCurrency.equalsIgnoreCase("DKK")){
                attraction.setPrice(price * USDDKK);
            }

            if (previousCurrency.equalsIgnoreCase("EUR")){
                attraction.setPrice(price * USDEUR);
            }
        }
    }

    private void convertCurrencyToEUR(List<TouristAttraction> attractionList){
        double EURDKK = eurRate / dkkRate;
        double EURUSD = eurRate / usdRate;

        for (TouristAttraction attraction:attractionList) {
            String previousCurrency = attraction.getCurrency();
            double price = attraction.getPrice();

            if (previousCurrency.equalsIgnoreCase("DKK")){
                attraction.setPrice(price * EURDKK);
            }

            if (previousCurrency.equalsIgnoreCase("USD")){
                attraction.setPrice(price * EURUSD);
            }
        }
    }
}

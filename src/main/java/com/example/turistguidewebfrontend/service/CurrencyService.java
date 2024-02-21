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

    public List<String> getAllCurrencies() {
        CurrencyRates currencyRates = new CurrencyRates();
        List<String> rates = currencyRates.getCurrencyList();
        return rates;
    }

    public double getConversionFactor(String currency) {

        try {
            switch (currency) {
                case "DKK" -> {
                    return 1;
                }
                case "USD" -> {
                    double USDDKK = getRates().getUSD() / getRates().getDKK();
                    return USDDKK;
                }
                case "EUR" -> {
                    double EURDKK = getRates().getEUR() / getRates().getDKK();
                    return EURDKK;
                }
            }
        }catch (IOException IOE){
            IOE.printStackTrace();
        }

        return 1; //redundant bliver aldrig ramt
    }
}

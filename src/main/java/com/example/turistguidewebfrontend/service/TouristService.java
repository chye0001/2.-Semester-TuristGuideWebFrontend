package com.example.turistguidewebfrontend.service;

import com.example.turistguidewebfrontend.model.TouristAttraction;
import com.example.turistguidewebfrontend.repository.TouristRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TouristService {

    private final TouristRepository touristRepository;

    public TouristService(TouristRepository touristRepository){
        this.touristRepository = touristRepository;
    }

    public TouristAttraction read(String touristAttractionName){
        TouristAttraction readAttraction;
        readAttraction = touristRepository.read(touristAttractionName);

        return readAttraction;
    }

    public List<TouristAttraction> viewAll(){
        return touristRepository.viewAll();
    }

    public TouristAttraction create(TouristAttraction touristAttraction){
        return touristRepository.create(touristAttraction);
    }

    public TouristAttraction update(TouristAttraction touristAttraction){
        return touristRepository.update(touristAttraction);
    }

    public TouristAttraction delete(String name){
        return touristRepository.delete(name);
    }

    public List<String> getCitySelections(){
        return touristRepository.getCitySelections();
    }

    public List<String> getTagSelections(){
        return touristRepository.getTagSelections();
    }

    public List<String> getAttractionTags(String name) {
        return touristRepository.getAttractionTags(name);
    }

    public void convertCurrencies(String currency) {
        touristRepository.convertCurrencies(currency);
    }
}

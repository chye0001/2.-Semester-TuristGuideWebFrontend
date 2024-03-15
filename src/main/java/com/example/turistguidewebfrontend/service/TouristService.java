package com.example.turistguidewebfrontend.service;

import com.example.turistguidewebfrontend.model.TouristAttraction;
import com.example.turistguidewebfrontend.repository.TouristRepositoryJDBC;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TouristService {

//    private final TouristRepository repositoryJDBC;
//    public TouristService(TouristRepository touristRepository){
//        this.touristRepository = touristRepository;
//    }

    private final TouristRepositoryJDBC repositoryJDBC;
    public TouristService(TouristRepositoryJDBC repositoryJDBC) {
        this.repositoryJDBC = repositoryJDBC;
    }

    public TouristAttraction getAttractionOnName(String touristAttractionName){
        TouristAttraction readAttraction;
        readAttraction = repositoryJDBC.getAttractionOnName(touristAttractionName);

        return readAttraction;
    }

    public List<TouristAttraction> getAllAttractions(){
        return repositoryJDBC.getAllAttractions();
    }

    public void create(TouristAttraction touristAttraction){
        repositoryJDBC.createAttraction(touristAttraction);
    }

    public TouristAttraction update(TouristAttraction touristAttraction){
        return repositoryJDBC.update(touristAttraction);
    }

    public void delete(String name){
        repositoryJDBC.deleteAttraction(name);
    }

    public List<String> getCitySelections(){
        return repositoryJDBC.getCitySelections();
    }

    public List<String> getTagSelections(){
        return repositoryJDBC.getTagSelections();
    }

    public List<String> getAttractionTagsByName(String name) {
        return repositoryJDBC.getTagsOnAttraction(name);
    }
}

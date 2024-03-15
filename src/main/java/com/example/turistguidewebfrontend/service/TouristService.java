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

    public TouristAttraction read(String touristAttractionName){
        TouristAttraction readAttraction;
        readAttraction = repositoryJDBC.read(touristAttractionName);

        return readAttraction;
    }

    public List<TouristAttraction> getAllAttractions(){
        return repositoryJDBC.getAllAttractions();
    }

    public void create(TouristAttraction touristAttraction){
        repositoryJDBC.create(touristAttraction);
    }

//    public TouristAttraction update(TouristAttraction touristAttraction){
//        return repositoryJDBC.update(touristAttraction);
//    }
//
//    public TouristAttraction delete(String name){
//        return repositoryJDBC.delete(name);
//    }

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

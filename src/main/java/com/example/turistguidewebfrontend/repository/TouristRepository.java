package com.example.turistguidewebfrontend.repository;

import com.example.turistguidewebfrontend.model.TouristAttraction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TouristRepository {

    private final List<TouristAttraction> database;
    private final List<String> citySelections = List.of("København", "Odense", "Kongens Lyngby");
    private final List<String> tagSelections = List.of("Børnevenlig", "Gratis", "Kunst", "Museum", "Natur");

    public TouristRepository(){
        this.database = new ArrayList<>(List.of(
                new TouristAttraction("SMK", "Statens Museum for Kunst", "København", List.of("Kunst", "Museum"), 99.95, "DKK"),
                new TouristAttraction("Odense Zoo", "Europas bedste zoo", "Odense", List.of("Børnevenlig"), 25.95, "DKK"),
                new TouristAttraction("Dyrehaven", "Naturpark med skovområder", "Kongens Lyngby", List.of("Natur", "Gratis"), 0.00, "DKK"),
                new TouristAttraction("Tivoli", "Forlystelsespark midt i København centrum", "København", List.of("Børnevenlig"), 249.95, "DKK")
        ));
    }

    public TouristAttraction read(String touristAttractionName){
        TouristAttraction viewTouristAttraction;

        try {
            for (TouristAttraction touristAttraction : database) {
                if (touristAttraction.getName().equalsIgnoreCase(touristAttractionName)) {
                    viewTouristAttraction = touristAttraction;
                    return viewTouristAttraction;
                }
            }
            return null;

        } catch (Exception e){
            return null;
        }
    }

    public List<TouristAttraction> viewAll(){
        return database;
    }

    public TouristAttraction create(TouristAttraction touristAttraction){
        database.add(touristAttraction);
        return touristAttraction;
    }

    public TouristAttraction update(TouristAttraction touristAttraction){
        TouristAttraction updatedTouristAttraction = null;

        for (int i = 0; i < database.size(); i++) {
            if (touristAttraction.getName().equalsIgnoreCase(database.get(i).getName())){
                updatedTouristAttraction = touristAttraction;
                database.set(i, updatedTouristAttraction);
                break;
            }
        }

        return updatedTouristAttraction;
    }

    public TouristAttraction delete(String name){
        TouristAttraction touristAttractionToDelete = null;

        for (int i = 0; i < database.size(); i++) {
            if (name.equalsIgnoreCase(database.get(i).getName())){
                touristAttractionToDelete = database.get(i);
                database.remove(touristAttractionToDelete);
                break;
            }
        }

        return touristAttractionToDelete;
    }

    public List<String> getCitySelections(){
        return citySelections;
    }

    public List<String> getTagSelections(){
        return tagSelections;
    }

    public List<String> getAttractionTags(String name) {
        List<String> tags = null;

        for (TouristAttraction attraction:database) {
            if (attraction.getName().equalsIgnoreCase(name)){
                tags = attraction.getTags();
                return tags;
            }
        }

        return tags;
    }
}
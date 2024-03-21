package com.example.turistguidewebfrontend.repository;

import com.example.turistguidewebfrontend.model.TouristAttraction;

import java.util.List;

public interface CRUDOperationsJDBC
{
    public List<TouristAttraction> getAllAttractions();

    public TouristAttraction getAttractionOnName(String touristAttractionName);

    public void addAttraction(TouristAttraction touristAttraction);

    public void updateAttraction(TouristAttraction touristAttraction);

    public void deleteAttraction(String name);

    public List<String> getCitySelections();

    public List<String> getTagSelections();

    public List<String> getTagsOnAttraction(String attractionName);
}

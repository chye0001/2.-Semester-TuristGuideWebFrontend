package com.example.turistguidewebfrontend.repository;

import com.example.turistguidewebfrontend.model.TouristAttraction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TouristRepositoryTest {

    TouristRepository repository;

    @BeforeEach
    void setUp() {
        repository = new TouristRepository();
//        Attractions in "database".
//        TouristAttraction("SMK", "Statens Museum for Kunst", "København", List.of("Kunst", "Museum"), 99.95),
//        TouristAttraction("Odense Zoo", "Europas bedste zoo", "Odense", List.of("Børnevenlig"), 25.95),
//        TouristAttraction("Dyrehaven", "Naturpark med skovområder", "Kongens Lyngby", List.of("Natur", "Gratis"), 0.00),
//        TouristAttraction("Tivoli", "Forlystelsespark midt i København centrum", "København", List.of("Børnevenlig"), 249.95)));
    }

    @Test
    void search_For_Tourist_Attraction_On_Name() {
        TouristAttraction expectedAttraction = new TouristAttraction("SMK", "Statens Museum for Kunst", "København", List.of("Kunst", "Museum"), 99);
        TouristAttraction actualAttraction = repository.read("SMK");
        assertEquals(expectedAttraction, actualAttraction);
    }

    @Test
    void search_For_None_Existing_Tourist_Attraction_On_Name() {
        TouristAttraction expectedAttraction = null;
        TouristAttraction actualAttraction = repository.read("Opera Huset");
        assertEquals(expectedAttraction, actualAttraction);
    }

    @Test
    void get_All_Tourist_Attractions() {
        List<TouristAttraction> expectedAttractionsList = new ArrayList<>(List.of(
                new TouristAttraction("SMK", "Statens Museum for Kunst", "København", List.of("Kunst", "Museum"), 99),
                new TouristAttraction("Odense Zoo", "Europas bedste zoo", "Odense", List.of("Børnevenlig"), 25),
                new TouristAttraction("Dyrehaven", "Naturpark med skovområder", "Kongens Lyngby", List.of("Natur", "Gratis"), 0),
                new TouristAttraction("Tivoli", "Forlystelsespark midt i København centrum", "København", List.of("Børnevenlig"), 249)));

        List<TouristAttraction> actualAttractionsList = repository.viewAll();

        assertEquals(expectedAttractionsList, actualAttractionsList);

    }

    @Test
    void test_Size_On_Tourist_Attraction() {
        int expectedAttractionsListSize = 4;
        int actualAttractionsListSize = repository.viewAll().size();

        assertEquals(expectedAttractionsListSize, actualAttractionsListSize);

    }

    @Test
    void create() {
        List<TouristAttraction> expectedAttractionsList = new ArrayList<>(List.of(
                new TouristAttraction("SMK", "Statens Museum for Kunst", "København", List.of("Kunst", "Museum"), 99),
                new TouristAttraction("Odense Zoo", "Europas bedste zoo", "Odense", List.of("Børnevenlig"), 25),
                new TouristAttraction("Dyrehaven", "Naturpark med skovområder", "Kongens Lyngby", List.of("Natur", "Gratis"), 0),
                new TouristAttraction("Tivoli", "Forlystelsespark midt i København centrum", "København", List.of("Børnevenlig"), 249),
                new TouristAttraction("Test", "Test", "Test", List.of("Test"), 0)));

        repository.create(new TouristAttraction("Test", "Test", "Test", List.of("Test"), 0));

        List<TouristAttraction> actualAttractionsList = repository.viewAll();

        assertEquals(expectedAttractionsList, actualAttractionsList);
    }

    @Test
    void update_Existing_Attraction() {
        TouristAttraction expectedUpdatedAttraction = new TouristAttraction("Tivoli", "Forlystelsespark midt i København centrum", "LYNGBYTEST", List.of("Børnevenlig"), 249);

        repository.update(new TouristAttraction("Tivoli", "Forlystelsespark midt i København centrum", "LYNGBYTEST", List.of("Børnevenlig"), 249));
        TouristAttraction actualUpdatedAttraction = repository.read("Tivoli");

        assertEquals(expectedUpdatedAttraction, actualUpdatedAttraction);
    }

    @Test
    void update_None_Existing_Attraction() {
        TouristAttraction expectedUpdatedAttraction = null;

        TouristAttraction actualUpdatedAttraction = repository.update(
                new TouristAttraction("ikkeEksisterendeAttraction", "Forlystelsespark midt i København centrum", "LYNGBYTEST", List.of("Børnevenlig"), 249));

        assertEquals(expectedUpdatedAttraction, actualUpdatedAttraction);
    }

    @Test
    void delete_Existing_Attraction() {
        int expectedSize = 3;

        repository.delete("Tivoli");
        int actualSize = repository.viewAll().size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    void delete_None_Existing_Attraction() {
        int expectedSize = 4;

        repository.delete("NoneExistingAttraction");
        int actualSize = repository.viewAll().size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    void get_Attraction_Tags_On_Existing_Attraction() {
//      Attraction with tags that is tested on:
//      TouristAttraction("SMK", "Statens Museum for Kunst", "København", List.of("Kunst", "Museum"), 99.95);
        List<String> expectedTags = new ArrayList<>(List.of("Kunst", "Museum"));

        List<String> actualTags = repository.getAttractionTags("SMK");

        assertEquals(expectedTags, actualTags);
    }

    @Test
    void get_Attraction_Tags_On_None_Existing_Attraction() {
        List<String> expectedTags = null;

        List<String> actualTags = repository.getAttractionTags("noneExistingAttraction");

        assertEquals(expectedTags, actualTags);
    }
}
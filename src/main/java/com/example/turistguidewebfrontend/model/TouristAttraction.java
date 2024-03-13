package com.example.turistguidewebfrontend.model;

import java.util.List;
import java.util.Objects;

public class TouristAttraction {

    private String name;
    private String description;
    private String city;
    private List<String> tags;
    private int price;
    private String currencyCode;

    public TouristAttraction(){
        this.currencyCode ="DKK";
    }

    // gammel, blev andet med den hardkodet arraylist som database.
    public TouristAttraction(String name,
                             String description,
                             String city,
                             List<String> tags,
                             int price){
        this.name = name;
        this.description = description;
        this.city = city;
        this.tags = tags;
        this.price = price;
        this.currencyCode = "DKK";
    }

    //Ny bliver brugt i JDBCrepository.
    public TouristAttraction(String name,
                             String description,
                             String city,
                             List<String> tags,
                             int price,
                             String currencyCode){
        this.name = name;
        this.description = description;
        this.city = city;
        this.tags = tags;
        this.price = price;
        this.currencyCode = currencyCode;
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public String getCity() {
        return city;
    }
    public List<String> getTags() {
        return tags;
    }
    public double getPrice(){
        return price;
    }
    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setTags(List<String> tags) {
        this.tags = tags;
    }
    public void setPrice(int price){
        this.price = price;
    }
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TouristAttraction that)) return false;
//        if(name == null && that.getName() == null) return true;
        return Double.compare(price, that.price) == 0 && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(city, that.city) && Objects.equals(tags, that.tags) && Objects.equals(currencyCode, that.currencyCode);
    }
}


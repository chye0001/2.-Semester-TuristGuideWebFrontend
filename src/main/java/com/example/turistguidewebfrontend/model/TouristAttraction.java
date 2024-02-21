package com.example.turistguidewebfrontend.model;

import java.util.List;

public class TouristAttraction {

    private String name;
    private String description;
    private String city;
    private List<String> tags;
    private double price;
    private String currency;

    public TouristAttraction(){}
    public TouristAttraction(String name,
                             String description,
                             String city,
                             List<String> tags,
                             double price){
        this.name = name;
        this.description = description;
        this.city = city;
        this.tags = tags;
        this.price = price;
        this.currency = "DKK";
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
    public String getCurrency() {
        return currency;
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
    public void setPrice(double price){
        this.price = price;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
}


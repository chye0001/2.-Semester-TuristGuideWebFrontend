package com.example.turistguidewebfrontend.model;

import java.util.List;
import java.util.Objects;

public class TouristAttraction {

    private String name;
    private String description;
    private String city;
    private List<String> tags;
    private double price;
    private String currency;

    public TouristAttraction(){
        this.currency="DKK";
    }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TouristAttraction that)) return false;
//        if(name == null && that.getName() == null) return true;
        return Double.compare(price, that.price) == 0 && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(city, that.city) && Objects.equals(tags, that.tags) && Objects.equals(currency, that.currency);
    }
}


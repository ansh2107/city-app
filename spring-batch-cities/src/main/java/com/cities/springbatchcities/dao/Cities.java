package com.cities.springbatchcities.dao;

import java.util.List;

public class Cities {

    private int totalCities;

    public int getTotalCities() {
        return totalCities;
    }

    public void setTotalCities(int totalCities) {
        this.totalCities = totalCities;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    private List<City> cities;

    public Cities(int totalCities, List<City> cities) {
        this.totalCities = totalCities;
        this.cities = cities;
    }
}

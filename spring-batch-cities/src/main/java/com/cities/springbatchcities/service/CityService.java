package com.cities.springbatchcities.service;

import com.cities.springbatchcities.dao.City;
import com.cities.springbatchcities.repo.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    @Autowired
    private CityRepository repository;

    public List<City> findAll(Pageable page) {
        return repository.findAll(page).toList();
    }


    public City findById(Long id) {
        return repository.findById(id).orElse(new City());
    }

    public int getCountOfCities() {
        return (int) repository.count();
    }

    public void updateCityById(Long id, City updateCity) {
        City city = this.findById(id);
        city.setName(updateCity.getName());
        city.setPhoto(updateCity.getPhoto());
        repository.save(city);
    }

    public List<City> findCityByName(String name) {
        return repository.findByNameStartingWithIgnoreCase(name);
    }
}
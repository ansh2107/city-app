package com.cities.springbatchcities.config;

import com.cities.springbatchcities.dao.City;
import org.springframework.batch.item.ItemProcessor;

public class CityProcessor implements ItemProcessor<City, City> {
    @Override
    public City process(City city) throws Exception {
        return city;
    }

}

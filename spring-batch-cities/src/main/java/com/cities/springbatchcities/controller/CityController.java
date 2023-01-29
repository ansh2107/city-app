package com.cities.springbatchcities.controller;

import com.cities.springbatchcities.dao.Cities;
import com.cities.springbatchcities.dao.City;
import com.cities.springbatchcities.dao.Response;
import com.cities.springbatchcities.service.CityService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class CityController {

    @Autowired
    private CityService cityService;

    @RequestMapping(
            value = "/cities/{page}/{size}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Cities findCities(HttpServletResponse response, @PathVariable int page, @PathVariable int size) {

        Pageable pageRequest = PageRequest.of(page, size);

        int totalCities = cityService.getCountOfCities();
        List<City> cities = cityService.findAll(pageRequest);

        JSONObject cityObj = new JSONObject();
        cityObj.put("totalCities", totalCities);
        cityObj.put("cities", cities);

        return new Cities(totalCities, cities);
    }


    @RequestMapping(
            value = "/update-city/{id}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response updateCityById(HttpServletResponse response, @PathVariable Long id, @RequestBody  City city) {
        cityService.updateCityById(id, city);
        return new Response("Successfully updated city data", 200, "");
    }


    @RequestMapping(
            value = "/cities/{name}",
            method = RequestMethod.GET)
    public Cities findCitiesByName(HttpServletResponse response, @PathVariable String name) {
        List<City> cities = cityService.findCityByName(name);
        return new Cities(cities.size(), cities);
    }

}

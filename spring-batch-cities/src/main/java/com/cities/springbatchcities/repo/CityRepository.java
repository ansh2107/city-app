package com.cities.springbatchcities.repo;

import com.cities.springbatchcities.dao.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    List<City> findByNameStartingWith(String prefix);

    List<City> findByNameStartingWithIgnoreCase(String name);
}

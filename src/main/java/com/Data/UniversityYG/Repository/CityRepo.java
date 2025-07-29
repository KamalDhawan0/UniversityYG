package com.Data.UniversityYG.Repository;

import com.Data.UniversityYG.Model.Cities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CityRepo extends JpaRepository<Cities,Long> {
    @Query("SELECT DISTINCT c.cityName FROM Cities c")
    List<String> findAllCityNames();
}

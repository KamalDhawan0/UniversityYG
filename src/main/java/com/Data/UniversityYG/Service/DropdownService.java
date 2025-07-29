package com.Data.UniversityYG.Service;


import com.Data.UniversityYG.Model.Universities;
import com.Data.UniversityYG.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DropdownService {

    @Autowired
    private UniversitiesRepo universitiesRepo;

    @Autowired
    private CoursesRepo coursesRepo;

    @Autowired
    private CityRepo cityRepo;


    public List<String> getAllUniversityNames() {
        return universitiesRepo.findAllNames();
    }

    public List<Universities> getFullUniversity(){ return universitiesRepo.findAll();}

    public List<String> getAllCourseNames() {
        return coursesRepo.findAllCourseNames();
    }

    public List<String> getAllNames(){
        return cityRepo.findAllCityNames();
    }





}

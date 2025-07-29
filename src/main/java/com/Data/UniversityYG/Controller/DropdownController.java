package com.Data.UniversityYG.Controller;


import com.Data.UniversityYG.Model.Universities;
import com.Data.UniversityYG.Service.DropdownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/names")
public class DropdownController {


    @Autowired
   private DropdownService dropdownService;

    @GetMapping("/universities")
    public List<String> getUniversityNames() {
        return dropdownService.getAllUniversityNames();
    }

    @GetMapping("/full/universities")
    public List<Universities> getAllUniversities(){
        return dropdownService.getFullUniversity();
    }

    @GetMapping("/courses")
    public List<String> getCourseNames() {
        return dropdownService.getAllCourseNames();
    }

    @GetMapping("/cities")
    public List<String> getCityNames(){
        return dropdownService.getAllNames();
    }


}

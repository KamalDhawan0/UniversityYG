package com.Data.UniversityYG.Controller;


import com.Data.UniversityYG.Model.University;
import com.Data.UniversityYG.Service.CourseService;
import com.Data.UniversityYG.Service.UniversitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/names")
public class DropdownController {

    @Autowired
    private UniversitiesService universityService;

    @Autowired
    private CourseService courseService;

    @GetMapping("/universities")
    public List<String> getUniversityNames() {
        return universityService.getAllUniversityNames();
    }

    @GetMapping("/courses")
    public List<String> getCourseNames() {
        return courseService.getAllCourseNames();
    }
}

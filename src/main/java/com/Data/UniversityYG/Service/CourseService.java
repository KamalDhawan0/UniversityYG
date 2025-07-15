package com.Data.UniversityYG.Service;

import com.Data.UniversityYG.Repository.CoursesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CoursesRepo coursesRepo;


    public List<String> getAllCourseNames() {
        return coursesRepo.findAllCourseNames();
    }

}

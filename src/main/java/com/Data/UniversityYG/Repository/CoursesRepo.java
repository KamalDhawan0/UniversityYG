package com.Data.UniversityYG.Repository;

import com.Data.UniversityYG.Model.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CoursesRepo extends JpaRepository<Courses,Long> {
    @Query("SELECT DISTINCT c.coursename FROM Courses c")
    List<String> findAllCourseNames();
}

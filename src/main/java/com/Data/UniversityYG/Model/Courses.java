package com.Data.UniversityYG.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "courses")
public class Courses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long courseid;

    @Column(name = "course_name")
    private String coursename;

    public Long getCourseid() {
        return courseid;
    }
    public void setCourseid(Long courseid) {
        this.courseid = courseid;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }
}

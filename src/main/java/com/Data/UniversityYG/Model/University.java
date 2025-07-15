package com.Data.UniversityYG.Model;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;


@Entity
@Table(name = "universitydetails")
public class University {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "university_id")
    private Long universityId;

    @NotBlank(message = "University name is required")
    @Size(max = 255)
    @Column(name = "university_name", nullable = false)
    private String universityName;

    @NotBlank(message = "Course name is required")
    @Size(max = 255)
    @Column(name = "course_name", nullable = false)
    private String courseName;

    @NotBlank(message = "Course link is required")
    @Size(max = 500)
    @Pattern(
            regexp = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$",
            message = "Course link must be a valid URL"
    )
    @Column(name = "course_link", nullable = false)
    private String courseLink;

    @NotBlank(message = "Course type is required")
    @Size(max = 100)
    @Column(name = "course_type", nullable = false)
    private String courseType;

    @ManyToOne
    @JoinColumn(name = "added_by_user_id")
    private User addedBy;

    public Long getUniversityId() {
        return universityId;
    }

    public void setUniversityId(Long universityId) {
        this.universityId = universityId;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseLink() {
        return courseLink;
    }

    public void setCourseLink(String courseLink) {
        this.courseLink = courseLink;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }
}


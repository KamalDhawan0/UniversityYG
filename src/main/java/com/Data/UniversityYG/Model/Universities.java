package com.Data.UniversityYG.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "universities")
public class Universities {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "university_id")
    private Long universityId;

    @Column(name = "university_name",nullable = false)
    private String universityName;



    public Universities(Long universityId, String universityName) {
        this.universityId = universityId;
        this.universityName = universityName;
    }

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
}

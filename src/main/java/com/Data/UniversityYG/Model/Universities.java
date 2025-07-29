package com.Data.UniversityYG.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "universities")
public class Universities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "university_id")
    private Long universityId;

    @Column(name = "university_name", nullable = false)
    private String universityName;

    @NotBlank(message = "University link is required")
    @Size(max = 500)
    @Pattern(
            regexp = "^(https?://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$",
            message = "University link must be a valid URL"
    )
    @Column(name = "university_link", nullable = false, unique = true)
    private String universityLink;

    @NotBlank(message = "University Type is Required")
    @Column(name = "university_type")
    private String universityType;

    @NotBlank(message = "Ownership type is required")
    @Column(name = "ownership_type")
    private String ownershipType;


    public Universities() {
    }


    public Universities(String universityName, String universityLink, String universityType, String ownershipType) {
        this.universityName = universityName;
        this.universityLink = universityLink;
        this.universityType = universityType;
        this.ownershipType = ownershipType;
    }

    // Getters and Setters without redundant annotations
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

    public String getUniversityLink() {
        return universityLink;
    }

    public void setUniversityLink(String universityLink) {
        this.universityLink = universityLink;
    }

    public String getUniversityType() {
        return universityType;
    }

    public void setUniversityType(String universityType) {
        this.universityType = universityType;
    }

    public String getOwnershipType() {
        return ownershipType;
    }

    public void setOwnershipType(String ownershipType) {
        this.ownershipType = ownershipType;
    }
}
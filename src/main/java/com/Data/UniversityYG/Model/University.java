package com.Data.UniversityYG.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

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

    @NotBlank(message = "University City is Required")
    @Column(name="university_city")
    private String city;

    @NotBlank(message = "University Location is Required")
    @Column(name="campus_location")
    private String campusLocation;

    @NotBlank(message = "Campus Address is Required")
    @Column(name="campus_address")
    private String campusAddress;

    @NotBlank(message = "Do Tell Teaching Language")
    @Column(name = "moi")
    private String moi;

    @NotBlank(message = "Programme is Required")
    @Column(name = "programme")
    private String programme;

    @NotBlank(message = "Course link is required")
    @Size(max = 500)
    @Pattern(regexp = "^(https?://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$", message = "Course link must be a valid URL")
    @Column(name = "course_link", nullable = false)
    private String courseLink;

    @Column(name = "duration_in_years")
    private Integer durationInYears;

    @Column(name = "application_start_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicationStartDate;

    @Column(name = "first_step_deadline")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate firstStepDeadline;

    @Column(name = "deadline")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadLine;

    @Column(name = "application_fees")
    @NotNull(message = "Application Fees are Required")
    private Long applicationFees;

    @Column(name = "tuition_fees")
    @NotNull(message = "Tuition Fees are Required")
    private Long tuitionFees;


    @NotBlank(message = "Course type is required")
    @Size(max = 100)
    @Column(name = "course_type", nullable = false)
    private String courseType;

    @Enumerated(EnumType.STRING)
    @Column(name = "german_proficiency_level")
    private GermanProficiencyLevel germanProficiencyLevel;

    @Column(name = "gre_score")
    private Long greScore;

    @Column(name = "gmat_score")
    private Long gmatScore;

    @Column(name = "work_experience")
    @NotNull(message="Please mention Work experience. Mark 0 if Fresher")
    private Long workExperience;

    @Column(name = "application_mode")
    @NotBlank(message = "This Field is Required")
    private String applicationMode;

    @Column(name = "post_requirement")
    private String postRequirement;

    @NotBlank(message = "Deadline link is required")
    @Size(max = 500)
    @Pattern(regexp = "^(https?://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$", message = "Deadline link must be a valid URL")
    @Column(name = "deadline_link", nullable = false)
    private String deadlineLink;

    @NotBlank(message = "Login link is required")
    @Size(max = 500)
    @Pattern(regexp = "^(https?://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$", message = "Login link must be a valid URL")
    @Column(name = "login_link", nullable = false)
    private String loginLink;

    @Column(name = "pre_application_test")
    private String preApplicationTest;

    @Column(name = "additional_requirements")
    private String additionalRequirements;

    @ManyToOne
    @JoinColumn(name = "added_by_user_id")
    private User addedBy;

    @CreationTimestamp
    @Column(name = "added_on", nullable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate addedOn;

    @ManyToOne
    @JoinColumn(name = "updated_by_user_id")
    private User updatedBy;

    @UpdateTimestamp
    @Column(name = "updated_on")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate updatedOn;


    @Column(name = "ielts_score")
    private Long ieltsScore;

    @Column(name = "toefl_score")
    private Long toeflScore;

    @Column(name = "pte_score")
    private Long pteScore;

    @Column(name = "duolingo_score")
    private Long duilongoScore;

    @Column(name = "tuition_fee_period")
    @NotBlank(message = "Period is Required")
    private String tuitionFeePeriod;

    @NotBlank(message = "this field is required")
    @Column(name ="academic_level")
    private String academicLevel;


    @Column(name = "specialisation")
    private String specialisation;

    @Column(name = "cgpa")
    private Double cgpa;

    @Column(name = "percentage")
    private Double percentage;

    // --- GETTERS AND SETTERS ---


    public String getAcademicLevel() {
        return academicLevel;
    }

    public void setAcademicLevel (String academicLevel) {
        this.academicLevel = academicLevel;
    }

    public String getSpecialisation() {
        return specialisation;
    }

    public void setSpecialisation(String specialisation) {
        this.specialisation = specialisation;
    }

    public Double getCgpa() {
        return cgpa;
    }

    public void setCgpa(Double cgpa) {
        this.cgpa = cgpa;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public String getTuitionFeePeriod() {
        return tuitionFeePeriod;
    }

    public void setTuitionFeePeriod(String tuitionFeePeriod) {
        this.tuitionFeePeriod = tuitionFeePeriod;
    }

    public Long getIeltsScore() {
        return ieltsScore;
    }

    public void setIeltsScore(Long ieltsScore) {
        this.ieltsScore = ieltsScore;
    }

    public Long getToeflScore() {
        return toeflScore;
    }

    public void setToeflScore(Long toeflScore) {
        this.toeflScore = toeflScore;
    }

    public Long getPteScore() {
        return pteScore;
    }

    public void setPteScore(Long pteScore) {
        this.pteScore = pteScore;
    }

    public Long getDuilongoScore() {
        return duilongoScore;
    }

    public void setDuilongoScore(Long duilongoScore) {
        this.duilongoScore = duilongoScore;
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

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCampusLocation() {
        return campusLocation;
    }

    public void setCampusLocation(String campusLocation) {
        this.campusLocation = campusLocation;
    }

    public String getCampusAddress() {
        return campusAddress;
    }

    public void setCampusAddress(String campusAddress) {
        this.campusAddress = campusAddress;
    }

    public String getMoi() {
        return moi;
    }

    public void setMoi(String moi) {
        this.moi = moi;
    }

    public String getProgramme() {
        return programme;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }

    public String getCourseLink() {
        return courseLink;
    }

    public void setCourseLink(String courseLink) {
        this.courseLink = courseLink;
    }

    public Integer getDurationInYears() {
        return durationInYears;
    }

    public void setDurationInYears(Integer durationInYears) {
        this.durationInYears = durationInYears;
    }

    public LocalDate getApplicationStartDate() {
        return applicationStartDate;
    }

    public void setApplicationStartDate(LocalDate applicationStartDate) {
        this.applicationStartDate = applicationStartDate;
    }

    public LocalDate getFirstStepDeadline() {
        return firstStepDeadline;
    }

    public void setFirstStepDeadline(LocalDate firstStepDeadline) {
        this.firstStepDeadline = firstStepDeadline;
    }

    public LocalDate getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(LocalDate deadLine) {
        this.deadLine = deadLine;
    }

    public Long getApplicationFees() {
        return applicationFees;
    }

    public void setApplicationFees(Long applicationFees) {
        this.applicationFees = applicationFees;
    }

    public Long getTuitionFees() {
        return tuitionFees;
    }

    public void setTuitionFees(Long tuitionFees) {
        this.tuitionFees = tuitionFees;
    }


    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public GermanProficiencyLevel getGermanProficiencyLevel() {
        return germanProficiencyLevel;
    }

    public void setGermanProficiencyLevel(GermanProficiencyLevel germanProficiencyLevel) {
        this.germanProficiencyLevel = germanProficiencyLevel;
    }

    public Long getGreScore() {
        return greScore;
    }

    public void setGreScore(Long greScore) {
        this.greScore = greScore;
    }

    public Long getGmatScore() {
        return gmatScore;
    }

    public void setGmatScore(Long gmatScore) {
        this.gmatScore = gmatScore;
    }

    public Long getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(Long workExperience) {
        this.workExperience = workExperience;
    }

    public String getApplicationMode() {
        return applicationMode;
    }

    public void setApplicationMode(String applicationMode) {
        this.applicationMode = applicationMode;
    }

    public String getPostRequirement() {
        return postRequirement;
    }

    public void setPostRequirement(String postRequirement) {
        this.postRequirement = postRequirement;
    }

    public String getDeadlineLink() {
        return deadlineLink;
    }

    public void setDeadlineLink(String deadlineLink) {
        this.deadlineLink = deadlineLink;
    }

    public String getLoginLink() {
        return loginLink;
    }

    public void setLoginLink(String loginLink) {
        this.loginLink = loginLink;
    }

    public String getPreApplicationTest() {
        return preApplicationTest;
    }

    public void setPreApplicationTest(String preApplicationTest) {
        this.preApplicationTest = preApplicationTest;
    }

    public String getAdditionalRequirements() {
        return additionalRequirements;
    }

    public void setAdditionalRequirements(String additionalRequirements) {
        this.additionalRequirements = additionalRequirements;
    }

    public User getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(User addedBy) {
        this.addedBy = addedBy;
    }

    public LocalDate getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(LocalDate addedOn) {
        this.addedOn = addedOn;
    }

    public User getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(User updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
    }
}

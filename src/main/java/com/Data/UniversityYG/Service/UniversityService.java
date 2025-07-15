package com.Data.UniversityYG.Service;

import com.Data.UniversityYG.Model.University;
import com.Data.UniversityYG.Repository.UniversityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UniversityService {

    @Autowired
    private UniversityRepo universityRepo;

    // CREATE
    public University addUniversity(University university) {
        return universityRepo.save(university);
    }

    // READ ALL
    public List<University> getAllUniversities() {
        return universityRepo.findAll();
    }

    // READ ONE
    public Optional<University> getUniversityById(Long id) {
        return universityRepo.findById(id);
    }

    // UPDATE
    public University updateUniversity(Long id, University updatedUniversity) {
        return universityRepo.findById(id)
                .map(existing -> {
                    existing.setUniversityName(updatedUniversity.getUniversityName());
                    existing.setCourseName(updatedUniversity.getCourseName());
                    existing.setCourseLink(updatedUniversity.getCourseLink());
                    existing.setCourseType(updatedUniversity.getCourseType());
                    return universityRepo.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("University not found with ID: " + id));
    }

    // DELETE
    public void deleteUniversity(Long id) {
        if (!universityRepo.existsById(id)) {
            throw new RuntimeException("University not found with ID: " + id);
        }
        universityRepo.deleteById(id);
    }

}

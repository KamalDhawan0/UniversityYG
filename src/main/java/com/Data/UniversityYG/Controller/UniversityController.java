package com.Data.UniversityYG.Controller;

import com.Data.UniversityYG.Model.University;
import com.Data.UniversityYG.Service.UniversityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/universities")
public class UniversityController {

    @Autowired
    public UniversityService universityService;


    @PostMapping
    public ResponseEntity<University> createUniversity(@Valid @RequestBody University university) {
        University savedUniversity = universityService.addUniversity(university);
        return ResponseEntity.ok(savedUniversity);
    }


    @GetMapping
    public ResponseEntity<List<University>> getAllUniversities() {
        List<University> universities = universityService.getAllUniversities();
        return ResponseEntity.ok(universities);
    }


    @GetMapping("/{id}")
    public ResponseEntity<University> getUniversityById(@PathVariable Long id) {
        return universityService.getUniversityById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}")
    public ResponseEntity<University> updateUniversity(
            @PathVariable Long id,
            @Valid @RequestBody University university) {
        University updatedUniversity = universityService.updateUniversity(id, university);
        return ResponseEntity.ok(updatedUniversity);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUniversity(@PathVariable Long id) {
        universityService.deleteUniversity(id);
        return ResponseEntity.noContent().build();
    }
}

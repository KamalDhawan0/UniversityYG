package com.Data.UniversityYG.Controller;

import com.Data.UniversityYG.Model.University;
import com.Data.UniversityYG.Service.UniversityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/universities")
public class UniversityController {

    @Autowired
    public UniversityService universityService;

    /**
     * FIX: Added the 'Principal' parameter. Spring Security will automatically
     * inject this with the details of the currently authenticated user.
     */
    @PostMapping
    public ResponseEntity<University> createUniversity(@Valid @RequestBody University university, Principal principal) {
        // We get the username from the principal...
        String username = principal.getName();
        // ...and pass it to the service method.
        University savedUniversity = universityService.addUniversity(university, username);
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

    /**
     * FIX: Added the 'Principal' parameter here as well for the update operation.
     */
    @PutMapping("/{id}")
    public ResponseEntity<University> updateUniversity(
            @PathVariable Long id,
            @Valid @RequestBody University university, Principal principal) {
        // Get the username of the user performing the update...
        String username = principal.getName();
        // ...and pass it to the service method along with the ID and university data.
        University updatedUniversity = universityService.updateUniversity(id, university, username);
        return ResponseEntity.ok(updatedUniversity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUniversity(@PathVariable Long id) {
        universityService.deleteUniversity(id);
        return ResponseEntity.noContent().build();
    }
}

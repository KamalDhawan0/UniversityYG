package com.Data.UniversityYG.Service;

import com.Data.UniversityYG.Model.University;
import com.Data.UniversityYG.Model.User;
import com.Data.UniversityYG.Repository.UniversityRepo;
import com.Data.UniversityYG.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UniversityService {

    @Autowired
    private UniversityRepo universityRepo;

    @Autowired
    private UserRepo userRepository; // FIX 1: Inject the UserRepository

    // READ ALL
    public List<University> getAllUniversities() {
        return universityRepo.findAll();
    }

    // READ ONE
    public Optional<University> getUniversityById(Long id) {
        return universityRepo.findById(id);
    }

    /**
     * FIX 2: Modified to accept the current user's name to set the 'addedBy' field.
     * @param university The university data from the request.
     * @param username The username of the currently authenticated user.
     * @return The saved university entity.
     */
    @Transactional
    public University addUniversity(University university, String username) {
        // Find the user who is adding this entry
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // Set the user on the university entity
        university.setAddedBy(currentUser);

        return universityRepo.save(university);
    }

    /**
     * FIX 3: Modified to accept the current user's name and to update ALL fields.
     * @param id The ID of the university to update.
     * @param updatedUniversity The new university data from the request.
     * @param username The username of the currently authenticated user.
     * @return The updated university entity.
     */
    @Transactional
    public University updateUniversity(Long id, University updatedUniversity, String username) {
        // Find the user who is performing the update
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // Find the existing university record
        return universityRepo.findById(id)
                .map(existing -> {
                    // Set the user who updated the record
                    existing.setUpdatedBy(currentUser);

                    // --- Update ALL fields from the request ---
                    existing.setUniversityName(updatedUniversity.getUniversityName());
                    existing.setCourseName(updatedUniversity.getCourseName());
                    existing.setCity(updatedUniversity.getCity());
                    existing.setCampusLocation(updatedUniversity.getCampusLocation());
                    existing.setCampusAddress(updatedUniversity.getCampusAddress());
                    existing.setMoi(updatedUniversity.getMoi());
                    existing.setProgramme(updatedUniversity.getProgramme());
                    existing.setCourseLink(updatedUniversity.getCourseLink());

                    existing.setDurationInYears(updatedUniversity.getDurationInYears());

                    existing.setApplicationStartDate(updatedUniversity.getApplicationStartDate());
                    existing.setFirstStepDeadline(updatedUniversity.getFirstStepDeadline());
                    existing.setDeadLine(updatedUniversity.getDeadLine());
                    existing.setApplicationFees(updatedUniversity.getApplicationFees());
                    existing.setTuitionFees(updatedUniversity.getTuitionFees());

                    existing.setCgpa(updatedUniversity.getCgpa());
                    existing.setPercentage(updatedUniversity.getPercentage());
                    existing.setSpecialisation(updatedUniversity.getSpecialisation());
                    existing.setAcademicLevel(updatedUniversity.getAcademicLevel());

                    existing.setIeltsScore(updatedUniversity.getIeltsScore());
                    existing.setToeflScore(updatedUniversity.getToeflScore());
                    existing.setPteScore(updatedUniversity.getPteScore());
                    existing.setDuilongoScore(updatedUniversity.getDuilongoScore());
                    existing.setTuitionFeePeriod(updatedUniversity.getTuitionFeePeriod());
                    existing.setCourseType(updatedUniversity.getCourseType());
                    existing.setGermanProficiencyLevel(updatedUniversity.getGermanProficiencyLevel());
                    existing.setGreScore(updatedUniversity.getGreScore());
                    existing.setGmatScore(updatedUniversity.getGmatScore());
                    existing.setWorkExperience(updatedUniversity.getWorkExperience());
                    existing.setApplicationMode(updatedUniversity.getApplicationMode());
                    existing.setPostRequirement(updatedUniversity.getPostRequirement());
                    existing.setDeadlineLink(updatedUniversity.getDeadlineLink());
                    existing.setLoginLink(updatedUniversity.getLoginLink());
                    existing.setPreApplicationTest(updatedUniversity.getPreApplicationTest());
                    existing.setAdditionalRequirements(updatedUniversity.getAdditionalRequirements());

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

package com.Data.UniversityYG.Repository;

import com.Data.UniversityYG.Model.Universities;
import com.Data.UniversityYG.Model.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UniversitiesRepo extends JpaRepository<Universities,Long> {
    @Query("SELECT DISTINCT u.universityName FROM Universities u")
    List<String> findAllNames();
}

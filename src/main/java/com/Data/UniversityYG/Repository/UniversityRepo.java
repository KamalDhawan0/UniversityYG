package com.Data.UniversityYG.Repository;


import com.Data.UniversityYG.Model.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityRepo extends JpaRepository<University,Long> {
}

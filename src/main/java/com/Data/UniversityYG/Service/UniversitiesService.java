package com.Data.UniversityYG.Service;

import com.Data.UniversityYG.Model.University;
import com.Data.UniversityYG.Repository.UniversitiesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UniversitiesService {

        @Autowired
        private UniversitiesRepo universityRepository;

        public List<String> getAllUniversityNames() {
            return universityRepository.findAllNames();
        }
}

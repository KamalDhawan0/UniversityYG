package com.Data.UniversityYG.Repository;

import com.Data.UniversityYG.DTO.UserIdUsernameView;
import com.Data.UniversityYG.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    /**
     * This is the method that was missing.
     * Spring Data JPA will automatically create the implementation for this method.
     * It will search for a User entity in the database that has a 'username' field
     * matching the provided username string.
     *
     * @param username The username to search for.
     * @return An Optional containing the User if found, or an empty Optional if not.
     */
    Optional<User> findByUsername(String username);

    @Query("select u.id as id, u.username as username from User u")
    List<UserIdUsernameView> findAllIdAndUsername();





}

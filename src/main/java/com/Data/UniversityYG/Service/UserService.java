package com.Data.UniversityYG.Service;

import com.Data.UniversityYG.DTO.LoginRequestDto;
import com.Data.UniversityYG.DTO.UserRegisterRequestDto;
import com.Data.UniversityYG.Model.User;
import com.Data.UniversityYG.Repository.UserRepo;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepository;


    private PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }



    @Autowired
    private JwtService jwtService;

    public String login(LoginRequestDto dto, AuthenticationManager authenticationManager) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        return jwtService.generateToken(userDetails);
    }


    public String registerUser(UserRegisterRequestDto dto) {
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            return "Username already taken!";
        }

        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        User user = new User(dto.getUsername(), encodedPassword);
        userRepository.save(user);

        return "User registered successfully!";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}

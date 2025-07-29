package com.Data.UniversityYG.Controller;

import com.Data.UniversityYG.DTO.*;
import com.Data.UniversityYG.Model.User;
import com.Data.UniversityYG.Service.JwtService;
import com.Data.UniversityYG.Service.UserService;
import com.Data.UniversityYG.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {



    @Autowired
    private JwtService jwtService;

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;

    public AuthController(UserService userService, AuthenticationManager authenticationManager,AuthService authService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.authService = authService;
    }


    @GetMapping("/all")
    public List<UserIdUsernameView> getaAllUsers(){
        return userService.getallUsers();
    }



    @PostMapping("/register")
    public String register(@RequestBody UserRegisterRequestDto dto) {
        return userService.registerUser(dto);
    }



    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthRequest request) {
        String jwt = authService.login(request);
        return ResponseEntity.ok(Map.of("token", jwt)); // ✅ Proper JSON object
    }


}


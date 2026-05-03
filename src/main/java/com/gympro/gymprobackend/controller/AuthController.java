package com.gympro.gymprobackend.controller;

import com.gympro.gymprobackend.dto.JwtResponse;
import com.gympro.gymprobackend.dto.LoginRequest;
import com.gympro.gymprobackend.dto.RegisterRequest;
import com.gympro.gymprobackend.entity.ERole;
import com.gympro.gymprobackend.entity.Role;
import com.gympro.gymprobackend.repository.RoleRepository;
import com.gympro.gymprobackend.repository.UserRepository;
import com.gympro.gymprobackend.security.JwtUtils;
import com.gympro.gymprobackend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;



@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtUtils jwtUtils;

    // POST /auth/login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        // Authenticate: Spring calls loadUserByUsername + BCrypt verify
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()));

        // Set the authenticated user in the security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate JWT
        String jwt = jwtUtils.generateJwtToken(authentication);

        // Get user details to include in the response
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .collect(Collectors.toList());

        User user = userRepository.findByEmail(userDetails.getUsername()).get();

        // Return token + user info to Angular
        return ResponseEntity.ok(new JwtResponse(jwt, "Bearer",
                user.getId(), user.getEmail(), roles));
    }

    // POST /auth/register
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {

        // Check for duplicates
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity.badRequest()
                    .body("Error: Email is already in use");
        }

        // Create new user and hash the password
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        // Assign requested roles (default to ROLE_USER if none specified)
        Set<Role> roles = new HashSet<>();
        if (registerRequest.getRoles() == null || registerRequest.getRoles().isEmpty()) {
            roles.add(roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Role not found")));
        } else {
            registerRequest.getRoles().forEach(role -> {
                ERole eRole;
                if (role.equalsIgnoreCase("admin")) {
                    eRole = ERole.ROLE_ADMIN;
                } else if (role.equalsIgnoreCase("receptionist")) {
                    eRole = ERole.ROLE_RECEPTIONIST;
                } else {
                    eRole = ERole.ROLE_USER;
                }
                roles.add(roleRepository.findByName(eRole)
                        .orElseThrow(() -> new RuntimeException("Role not found: " + role)));
            });
        }

        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }
}
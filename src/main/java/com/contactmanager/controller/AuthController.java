package com.contactmanager.controller;

import com.contactmanager.entity.User;
import com.contactmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "https://contact-backend-5x30.onrender.com") // ✅ frontend port
public class AuthController {

    @Autowired
    private UserRepository repo;

    // ==============================
    // ✅ REGISTER USER
    // ==============================
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {

        // 🔥 validation
        if (user.getName() == null || user.getEmail() == null || user.getPassword() == null) {
            return ResponseEntity.badRequest().body("All fields are required");
        }

        // 🔍 check existing user
        Optional<User> existing = repo.findByEmail(user.getEmail());

        if (existing.isPresent()) {
            return ResponseEntity.badRequest().body("User already exists");
        }

        // ✅ save user
        repo.save(user);

        return ResponseEntity.ok("Registered successfully");
    }

    // ==============================
    // ✅ LOGIN USER
    // ==============================
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {

        // 🔥 validation
        if (user.getEmail() == null || user.getPassword() == null) {
            return ResponseEntity.badRequest().body("Email and password required");
        }

        // 🔍 find user
        Optional<User> optional = repo.findByEmail(user.getEmail());

        // ❌ user not found
        if (optional.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        User dbUser = optional.get();

        // ❌ wrong password
        if (!dbUser.getPassword().equals(user.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid password");
        }

        // ✅ success
        return ResponseEntity.ok(dbUser);
    }
}
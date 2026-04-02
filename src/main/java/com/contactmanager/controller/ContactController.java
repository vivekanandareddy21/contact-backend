package com.contactmanager.controller;

import com.contactmanager.entity.Contact;
import com.contactmanager.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@CrossOrigin(origins = {"http://localhost:5173", "https://contact-backend-5x30.onrender.com"}) // ✅ CORS
public class ContactController {

    @Autowired
    private ContactService service;

    // ✅ Get contacts (USER BASED 🔥)
    @GetMapping
    public List<Contact> getAll(@RequestParam String userEmail) {
        return service.getContactsByUser(userEmail);
    }

    // ✅ Get by ID
    @GetMapping("/{id}")
    public Contact getById(@PathVariable Long id) {
        return service.getById(id);
    }

    // ✅ Add contact
    @PostMapping
    public Contact addContact(@RequestBody Contact contact) {

        if (contact.getName() == null || contact.getEmail() == null || contact.getPhone() == null) {
            throw new RuntimeException("Required fields missing");
        }

        if (contact.getUserEmail() == null || contact.getUserEmail().isEmpty()) {
            throw new RuntimeException("User not provided");
        }

        return service.saveContact(contact);
    }

    // ✅ Update
    @PutMapping("/{id}")
    public Contact update(@PathVariable Long id, @RequestBody Contact contact) {
        return service.updateContact(id, contact);
    }

    // ✅ Delete
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteContact(id);
    }

    // ✅ Search (USER BASED 🔥)
    @GetMapping("/search")
    public List<Contact> search(
            @RequestParam String name,
            @RequestParam String userEmail
    ) {
        return service.searchContactsByUser(name, userEmail);
    }

    // ✅ Upload image
    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file) throws Exception {

        String uploadDir = "uploads/";
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        java.nio.file.Path path = java.nio.file.Paths.get(uploadDir + fileName);

        java.nio.file.Files.createDirectories(path.getParent());
        java.nio.file.Files.write(path, file.getBytes());

        return "http://localhost:8081/uploads/" + fileName;
    }

    // ⭐ Toggle favorite
    @PutMapping("/favorite/{id}")
    public Contact toggleFavorite(@PathVariable Long id) {
        Contact contact = service.getById(id);
        contact.setFavorite(!contact.isFavorite());
        return service.saveContact(contact);
    }
}
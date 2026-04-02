package com.contactmanager.service;

import com.contactmanager.entity.Contact;
import com.contactmanager.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    @Autowired
    private ContactRepository repo;

    // ❌ (OLD - NOT USED ANYMORE)
    // public List<Contact> getAllContacts() {
    //     return repo.findAll();
    // }

    // ✅ Get contacts by user (IMPORTANT 🔥)
    public List<Contact> getContactsByUser(String email) {
        return repo.findByUserEmail(email);
    }

    // ✅ Save contact (userEmail comes from frontend)
    public Contact saveContact(Contact contact) {
        return repo.save(contact);
    }

    // ✅ Get by ID
    public Contact getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact not found"));
    }

    // ✅ Update contact (SAFE UPDATE 🔥)
    public Contact updateContact(Long id, Contact contact) {

        Contact existing = getById(id);

        existing.setName(contact.getName());
        existing.setEmail(contact.getEmail());
        existing.setPhone(contact.getPhone());
        existing.setAddress(contact.getAddress());
        existing.setCategory(contact.getCategory());
        existing.setFavorite(contact.isFavorite());
        existing.setImageUrl(contact.getImageUrl());

        // 🔥 IMPORTANT: DO NOT CHANGE USER
        existing.setUserEmail(existing.getUserEmail());

        return repo.save(existing);
    }

    // ✅ Delete
    public void deleteContact(Long id) {
        repo.deleteById(id);
    }

    // ❌ OLD SEARCH
    // public List<Contact> searchContacts(String name) {
    //     return repo.findByNameContainingIgnoreCase(name);
    // }

    // ✅ USER-BASED SEARCH (IMPORTANT 🔥)
    public List<Contact> searchContactsByUser(String name, String email) {
        return repo.findByNameContainingIgnoreCaseAndUserEmail(name, email);
    }
}
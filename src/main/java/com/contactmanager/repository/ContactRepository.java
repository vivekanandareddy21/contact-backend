package com.contactmanager.repository;

import com.contactmanager.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {

	List<Contact> findByUserEmail(String userEmail);

	List<Contact> findByNameContainingIgnoreCaseAndUserEmail(String name, String userEmail);
}
package com.example.booking_service.repository;

import com.example.booking_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByName(String name);

    boolean existsByEmail(String email);
}

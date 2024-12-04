package com.hilmirafiff.airbnb_clone_be.repository;

import com.hilmirafiff.airbnb_clone_be.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByUsername(String username);
    User findByEmail(String email);
}


package com.hilmirafiff.airbnb_clone_be.repository;

import com.hilmirafiff.airbnb_clone_be.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PropertyRepository extends JpaRepository<Property, UUID> {

    boolean existsByTitleIgnoreCase(String propertyName);
}

package com.hilmirafiff.airbnb_clone_be.repository;

import com.hilmirafiff.airbnb_clone_be.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ImageRepository extends JpaRepository<Image, UUID> {

    @Query("SELECT b FROM Image b WHERE b.propertyId.id = :propertyId")
    List<Image> findByPropertyId(@Param("propertyId") UUID userId);
}
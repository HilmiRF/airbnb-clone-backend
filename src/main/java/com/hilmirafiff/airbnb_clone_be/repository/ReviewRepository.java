package com.hilmirafiff.airbnb_clone_be.repository;

import com.hilmirafiff.airbnb_clone_be.entity.Booking;
import com.hilmirafiff.airbnb_clone_be.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {

    @Query("SELECT b FROM Review b WHERE b.propertyId.id = :propertyId")
    List<Review> findByPropertyId(@Param("propertyId") UUID propertyId);
}

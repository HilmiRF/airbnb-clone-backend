package com.hilmirafiff.airbnb_clone_be.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "property_master")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Property {

    @Id
    @GeneratedValue
    private UUID id;

    private String title;
    private String description;
    private Double pricePerNight;
    private String location;

    @JsonBackReference("user-property")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_HOST_ID")
    private User hostId;

    @Builder.Default
    @ToString.Exclude
    @JsonManagedReference("property-review")
    @OneToMany(mappedBy = "propertyId", targetEntity = Review.class)
    private Set<Review> reviews = new LinkedHashSet<>();

    @Builder.Default
    @ToString.Exclude
    @JsonManagedReference("user-property")
    @OneToMany(mappedBy = "propertyId", targetEntity = Image.class)
    private Set<Image> images = new LinkedHashSet<>();

}
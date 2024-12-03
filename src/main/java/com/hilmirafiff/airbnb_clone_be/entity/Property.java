package com.hilmirafiff.airbnb_clone_be.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
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
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private String localization;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String host;

    private Double latitude;

    private Double longitude;

    private Double rating;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}


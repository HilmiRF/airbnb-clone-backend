package com.hilmirafiff.airbnb_clone_be.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "review")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Review {
    @Id
    @GeneratedValue
    private Long id;

    @JsonBackReference("property-review")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id")
    private Property propertyId;

    @JsonBackReference("user-review")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userId;

    private int rating;
    private String comment;
}

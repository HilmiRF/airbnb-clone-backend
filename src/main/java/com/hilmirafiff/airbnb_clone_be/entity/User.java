package com.hilmirafiff.airbnb_clone_be.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_master")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class User {
    @Id // to specify the unique key for this entity
    @GeneratedValue// for auto generating id
    private UUID userId;

    private String email;
    private String username;
    private String password;
    private String image;

    @CreatedBy
    private UUID createdBy;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedBy
    private UUID updatedBy;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_LEVEL_ID")
    private UserLevel userLevel;
}


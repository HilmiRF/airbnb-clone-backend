package com.hilmirafiff.airbnb_clone_be.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hilmirafiff.airbnb_clone_be.util.UserLevelLayerEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "user_level")
public class UserLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private UUID id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "LAYER_LEVEL")
    private UserLevelLayerEnum layerLevel;

    @Basic
    @UpdateTimestamp
    @Column(name = "UPDATED_ON")
    private LocalDateTime updatedOn;

    @Basic
    @CreationTimestamp
    @Column(name = "CREATED_ON")
    private LocalDateTime createdOn;

    @Column(name = "CREATED_BY")
    private UUID createdBy;

    @Column(name = "UPDATED_BY")
    private UUID updatedBy;

    @Builder.Default
    @ToString.Exclude
    @JsonManagedReference
    @OneToMany(mappedBy = "userLevel", targetEntity = User.class)
    private Set<User> users = new LinkedHashSet<>();
}

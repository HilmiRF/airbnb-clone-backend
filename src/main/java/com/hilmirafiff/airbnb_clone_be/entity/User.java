package com.hilmirafiff.airbnb_clone_be.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_master")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class User implements UserDetails {
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

    @JsonBackReference("user-userLevel")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_LEVEL_ID")
    private UserLevel userLevel;

    @Builder.Default
    @ToString.Exclude
    @JsonManagedReference("user-property")
    @OneToMany(mappedBy = "hostId", targetEntity = Property.class)
    private Set<Property> properties = new LinkedHashSet<>();

    @Builder.Default
    @ToString.Exclude
    @JsonManagedReference("user-booking")
    @OneToMany(mappedBy = "userId", targetEntity = Booking.class)
    private Set<Booking> bookings = new LinkedHashSet<>();

    @Builder.Default
    @ToString.Exclude
    @JsonManagedReference("user-review")
    @OneToMany(mappedBy = "userId", targetEntity = Review.class)
    private Set<Review> reviews = new LinkedHashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}


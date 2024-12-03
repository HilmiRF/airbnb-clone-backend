package com.hilmirafiff.airbnb_clone_be.repository;

import com.hilmirafiff.airbnb_clone_be.entity.UserLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserLevelRepository extends JpaRepository<UserLevel, UUID> {
}

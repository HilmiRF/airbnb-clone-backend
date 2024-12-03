package com.hilmirafiff.airbnb_clone_be.dto.response.auth;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpResponseDto {
    private UUID userId;
    private String email;
    private String username;
    private UUID createdBy;
    private LocalDateTime createdAt;
    private UUID updatedBy;
    private LocalDateTime updatedAt;
}


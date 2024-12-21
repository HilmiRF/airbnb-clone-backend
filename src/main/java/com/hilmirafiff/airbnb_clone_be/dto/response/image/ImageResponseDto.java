package com.hilmirafiff.airbnb_clone_be.dto.response.image;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ImageResponseDto {
    private UUID id;
    private String url;
    private String fileName;
    private LocalDateTime uploadedAt;
    private String userId;
    private String propertyId;
}

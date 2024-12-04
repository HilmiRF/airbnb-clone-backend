package com.hilmirafiff.airbnb_clone_be.dto.request.property;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hilmirafiff.airbnb_clone_be.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreatePropertyRequestDto {
    private String title;
    private String description;
    private Double pricePerNight;
    private String location;
    private String hostId;
}

package com.hilmirafiff.airbnb_clone_be.dto.response.property;

import com.hilmirafiff.airbnb_clone_be.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyResponseDto {
    private UUID id;
    private String title;
    private String description;
    private Double pricePerNight;
    private String location;
    private String hostId;
    private String imageUrl;
}

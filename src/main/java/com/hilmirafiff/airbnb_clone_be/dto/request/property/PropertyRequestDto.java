package com.hilmirafiff.airbnb_clone_be.dto.request.property;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hilmirafiff.airbnb_clone_be.entity.User;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyRequestDto {
    @NotBlank(message = "NOT_BLANK_OR_NULL")
    private UUID id;
}

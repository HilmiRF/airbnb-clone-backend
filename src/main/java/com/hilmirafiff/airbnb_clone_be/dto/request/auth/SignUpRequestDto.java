package com.hilmirafiff.airbnb_clone_be.dto.request.auth;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hilmirafiff.airbnb_clone_be.entity.UserLevel;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SignUpRequestDto {

    @NotBlank(message = "NOT_BLANK_OR_NULL")
    private String email;

    @NotBlank(message = "NOT_BLANK_OR_NULL")
    private String username;

    @NotBlank(message = "NOT_BLANK_OR_NULL")
    private String password;

    @NotBlank(message = "NOT_BLANK_OR_NULL")
    private String userLevelId;
}

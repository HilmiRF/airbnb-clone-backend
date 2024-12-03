package com.hilmirafiff.airbnb_clone_be.dto.request.auth;

import com.hilmirafiff.airbnb_clone_be.entity.UserLevel;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class SignUpRequestDto {

    @NotBlank(message = "NOT_BLANK_OR_NULL")
    private String email;

    @NotBlank(message = "NOT_BLANK_OR_NULL")
    private String username;

    @NotBlank(message = "NOT_BLANK_OR_NULL")
    private String password;
}

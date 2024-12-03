package com.hilmirafiff.airbnb_clone_be.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {
    @NotBlank(message = "NOT_BLANK_OR_NULL")
    private String username;

    @NotBlank(message = "NOT_BLANK_OR_NULL")
    private String password;
}

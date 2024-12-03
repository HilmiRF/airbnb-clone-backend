package com.hilmirafiff.airbnb_clone_be.dto.response.auth;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hilmirafiff.airbnb_clone_be.dto.response.session.UserLoginResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
    private String tokenType;
    private long expiresIn;
    private String accessToken;
    private UserLoginResponseDto user;
}


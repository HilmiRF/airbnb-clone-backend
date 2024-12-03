package com.hilmirafiff.airbnb_clone_be.service;

import com.hilmirafiff.airbnb_clone_be.dto.OutputSchemaDataResponseDto;
import com.hilmirafiff.airbnb_clone_be.dto.request.auth.LoginRequestDto;
import com.hilmirafiff.airbnb_clone_be.dto.request.auth.SignUpRequestDto;
import com.hilmirafiff.airbnb_clone_be.dto.response.auth.LoginResponseDto;
import com.hilmirafiff.airbnb_clone_be.dto.response.auth.SignUpResponseDto;
import com.hilmirafiff.airbnb_clone_be.entity.User;
import com.hilmirafiff.airbnb_clone_be.util.OutputSchemaResponseDto;

public interface AuthService {
    OutputSchemaDataResponseDto<LoginResponseDto> login(LoginRequestDto loginRequestDto) throws Exception;

    OutputSchemaDataResponseDto<SignUpResponseDto> signUp(SignUpRequestDto signUpRequestDto) throws Exception;

    User getEmailFromToken(String token);

    OutputSchemaResponseDto logoff(User user);

}


package com.hilmirafiff.airbnb_clone_be.service.impl;

import com.hilmirafiff.airbnb_clone_be.dto.OutputSchemaDataResponseDto;
import com.hilmirafiff.airbnb_clone_be.dto.request.auth.LoginRequestDto;
import com.hilmirafiff.airbnb_clone_be.dto.request.auth.SignUpRequestDto;
import com.hilmirafiff.airbnb_clone_be.dto.response.auth.LoginResponseDto;
import com.hilmirafiff.airbnb_clone_be.dto.response.auth.SignUpResponseDto;
import com.hilmirafiff.airbnb_clone_be.dto.response.session.UserLoginResponseDto;
import com.hilmirafiff.airbnb_clone_be.entity.User;
import com.hilmirafiff.airbnb_clone_be.exception.ApplicationException;
import com.hilmirafiff.airbnb_clone_be.exception.ApplicationWithParamException;
import com.hilmirafiff.airbnb_clone_be.repository.UserRepository;
import com.hilmirafiff.airbnb_clone_be.security.JwtTokenProvider;
import com.hilmirafiff.airbnb_clone_be.service.AuthService;
import com.hilmirafiff.airbnb_clone_be.service.UserService;
import com.hilmirafiff.airbnb_clone_be.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.text.MessageFormat;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Value("${key.ad.front:null}")
    private String keyFrontend;
    @Value("${key.ad.backend:null}")
    private String keyBackend;
    @Value("${app.jwt-expiration-milliseconds:null}")
    private long jwtExpirationTime;


    private final UserServiceImpl userServiceImpl;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final UserRepository userRepository;

    public AuthServiceImpl(UserServiceImpl userServiceImpl, JwtTokenProvider jwtTokenProvider, UserService userService, UserRepository userRepository){
        this.userServiceImpl = userServiceImpl;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public OutputSchemaDataResponseDto<LoginResponseDto> login(LoginRequestDto loginRequestDto) throws Exception {
        final String username = loginRequestDto.getUsername();
        final String strToEncrypt = loginRequestDto.getPassword();
        final User checkUser = userRepository.findByUsername(username);
        final String strKey = keyBackend;
        AES256.setKey(strKey);

        AES256.encrypt(strToEncrypt);
        String encryptedPass = AES256.getEncryptedString();

        User user;
        String token;
        if(Objects.equals(checkUser.getPassword(), encryptedPass)) {
            user = this.userServiceImpl.getUserByUsername(loginRequestDto.getUsername());
            token = this.jwtTokenProvider.generateToken(user);
            return OutputSchemaDataResponseDto.<LoginResponseDto>builder()
                    .status(AppConstant.Status.SUCCESS)
                    .reason("Login Success")
                    .data(mapToLoginResponseDto(token, jwtExpirationTime / 1000, mapToUserLoginResponseDto(user)))
                    .build();
        } else {
            throw new ApplicationWithParamException(AppErrorEnum.WRONG_USERNAME_OR_PASSWORD, AppMessageEnum.USER.getMessageEn(), null);
        }
    }

    @Override
    public OutputSchemaDataResponseDto<SignUpResponseDto> signUp(SignUpRequestDto signUpRequestDto) throws Exception {
        final String username = signUpRequestDto.getUsername();
        final User checkUser = userRepository.findByUsername(username);

        if(checkUser != null){
            throw new ApplicationWithParamException(AppErrorEnum.ALREADY_EXISTS, AppMessageEnum.USER.getMessageEn(), null);
        }

        final String strToEncrypt = signUpRequestDto.getPassword();
        final String strKey = keyBackend;
        AES256.setKey(strKey);

        AES256.encrypt(strToEncrypt);
        String encryptedPass = AES256.getEncryptedString();
        signUpRequestDto.setPassword(encryptedPass);
        User newUser = userServiceImpl.save(signUpRequestDto);
        return OutputSchemaDataResponseDto.<SignUpResponseDto>builder()
                .status(AppConstant.Status.SUCCESS)
                .reason("Sign Up Success")
                .data(new SignUpResponseDto(newUser.getUserId(), newUser.getEmail(), newUser.getUsername(), newUser.getCreatedBy(), newUser.getCreatedAt(), newUser.getUpdatedBy(), newUser.getUpdatedAt()))
                .build();
    }

    @Override
    public User getUserFromToken(String token) {
        String username = this.jwtTokenProvider.getUsername(token);
        return userService.getUserByUsername(username);
    }

    @Override
    public OutputSchemaResponseDto logoff(User user) {
        return OutputSchemaResponseDto.builder()
                .status(AppConstant.Status.SUCCESS)
                .reason("Logoff Success")
                .build();
    }

    private LoginResponseDto mapToLoginResponseDto(String accessToken, long expiresIn, UserLoginResponseDto user) {
        return LoginResponseDto.builder()
                .accessToken(accessToken)
                .expiresIn(expiresIn)
                .tokenType("Bearer")
                .user(user)
                .build();
    }

    private UserLoginResponseDto mapToUserLoginResponseDto(User user) {
        return UserLoginResponseDto.builder()
                .id(user.getUserId())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }
}


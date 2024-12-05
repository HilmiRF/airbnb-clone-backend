package com.hilmirafiff.airbnb_clone_be.controller;

import com.hilmirafiff.airbnb_clone_be.dto.GlobalResponseDto;
import com.hilmirafiff.airbnb_clone_be.dto.OutputSchemaDataResponseDto;
import com.hilmirafiff.airbnb_clone_be.dto.request.auth.LoginRequestDto;
import com.hilmirafiff.airbnb_clone_be.dto.request.auth.SignUpRequestDto;
import com.hilmirafiff.airbnb_clone_be.dto.response.auth.LoginResponseDto;
import com.hilmirafiff.airbnb_clone_be.dto.response.auth.SignUpResponseDto;
import com.hilmirafiff.airbnb_clone_be.dto.response.property.PropertyResponseDto;
import com.hilmirafiff.airbnb_clone_be.entity.User;
import com.hilmirafiff.airbnb_clone_be.service.AuthService;
import com.hilmirafiff.airbnb_clone_be.util.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Endpoint for Authentication")
public class AuthController {
    private final AuthService authService;
    private final MessageUtils messageUtils;

    public AuthController(AuthService authService, MessageUtils messageUtils){
        this.authService = authService;
        this.messageUtils = messageUtils;
    }

    @Operation(summary = "Login")
    @PostMapping("/login")
    public ResponseEntity<GlobalResponseDto<OutputSchemaDataResponseDto<LoginResponseDto>>> login(@Valid @RequestBody LoginRequestDto loginRequestDto) throws Exception {
        OutputSchemaDataResponseDto<LoginResponseDto> response = authService.login(loginRequestDto);
        if (Objects.equals(response.getStatus(), AppConstant.Status.SUCCESS)){
            GlobalResponseDto<OutputSchemaDataResponseDto<LoginResponseDto>> responseDto = messageUtils.successDto(response, AppErrorEnum.OK);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } else {
            GlobalResponseDto<OutputSchemaDataResponseDto<LoginResponseDto>> responseDto = messageUtils.successDto(response, AppErrorEnum.WRONG_USERNAME_OR_PASSWORD);
            return new ResponseEntity<>(responseDto, HttpStatus.UNAUTHORIZED);
        }
    }

    @Operation(summary = "Register")
    @PostMapping("/register")
    public ResponseEntity<GlobalResponseDto<OutputSchemaDataResponseDto<SignUpResponseDto>>> signUp(@Valid @RequestBody SignUpRequestDto signUpRequestDto) throws Exception {
        OutputSchemaDataResponseDto<SignUpResponseDto> response = authService.signUp(signUpRequestDto);
        if (Objects.equals(response.getStatus(), AppConstant.Status.SUCCESS)){
            GlobalResponseDto<OutputSchemaDataResponseDto<SignUpResponseDto>> responseDto = messageUtils.successDto(response, AppErrorEnum.CREATED);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } else {
            GlobalResponseDto<OutputSchemaDataResponseDto<SignUpResponseDto>> responseDto = messageUtils.alreadyExistDto(response, AppErrorEnum.ALREADY_EXISTS, AppMessageEnum.USER, signUpRequestDto.getUsername());
            return new ResponseEntity<>(responseDto, HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "Logoff")
    @PostMapping("/logoff")
    public ResponseEntity<GlobalResponseDto<OutputSchemaResponseDto>> logoff (@RequestHeader HttpHeaders requestHeaders){
        String token = Objects.requireNonNull(requestHeaders.getFirst(HttpHeaders.AUTHORIZATION)).split(" ")[1];
        User user = this.authService.getUserFromToken(token);
        return new ResponseEntity<>(messageUtils.successDto(authService.logoff(user), AppErrorEnum.LOGOFF_SUCCESS), HttpStatus.OK);
    }
}

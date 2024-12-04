package com.hilmirafiff.airbnb_clone_be.controller;

import com.hilmirafiff.airbnb_clone_be.dto.GlobalResponseDto;
import com.hilmirafiff.airbnb_clone_be.dto.OutputSchemaDataResponseDto;
import com.hilmirafiff.airbnb_clone_be.dto.request.property.CreatePropertyRequestDto;
import com.hilmirafiff.airbnb_clone_be.dto.response.property.PropertyResponseDto;
import com.hilmirafiff.airbnb_clone_be.entity.User;
import com.hilmirafiff.airbnb_clone_be.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import com.hilmirafiff.airbnb_clone_be.service.PropertyService;
import com.hilmirafiff.airbnb_clone_be.util.AppConstant;
import com.hilmirafiff.airbnb_clone_be.util.AppErrorEnum;
import com.hilmirafiff.airbnb_clone_be.util.MessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Objects;

@RestController
@RequestMapping("property")
@Tag(name = "Property", description = "Endpoints for Property")
@Slf4j
public class PropertyController {

    private final PropertyService propertyService;
    private final MessageUtils messageUtils;

    private final AuthService authService;

    public PropertyController(PropertyService propertyService, MessageUtils messageUtils, AuthService authService) {
        this.propertyService = propertyService;
        this.messageUtils = messageUtils;
        this.authService = authService;
    }

    // Create new property
    @Operation(summary = "Create a property")
    @PostMapping
    public ResponseEntity<GlobalResponseDto<OutputSchemaDataResponseDto<PropertyResponseDto>>>createProperty(@RequestHeader HttpHeaders requestHeaders, @Valid @RequestBody CreatePropertyRequestDto createPropertyRequestDto) throws Exception {
        log.info("MASUK SINI");
        String token = Objects.requireNonNull(requestHeaders.getFirst(HttpHeaders.AUTHORIZATION)).split(" ")[1];
        User user = this.authService.getUserFromToken(token);
        OutputSchemaDataResponseDto<PropertyResponseDto> response = propertyService.createProperty(user, createPropertyRequestDto);
        if (Objects.equals(response.getStatus(), AppConstant.Status.SUCCESS)){
            GlobalResponseDto<OutputSchemaDataResponseDto<PropertyResponseDto>> responseDto = messageUtils.successDto(response, AppErrorEnum.CREATED);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } else {
            GlobalResponseDto<OutputSchemaDataResponseDto<PropertyResponseDto>> responseDto = messageUtils.successDto(response, AppErrorEnum.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "test property")
    @GetMapping
    public String test(){
        return "hello world";
    }

    // TO DO
    // Get all properties
    // Get property by id
}

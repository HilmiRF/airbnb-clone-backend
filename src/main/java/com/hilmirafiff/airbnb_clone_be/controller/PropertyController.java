package com.hilmirafiff.airbnb_clone_be.controller;

import com.hilmirafiff.airbnb_clone_be.dto.GlobalResponseDto;
import com.hilmirafiff.airbnb_clone_be.dto.OutputSchemaDataResponseDto;
import com.hilmirafiff.airbnb_clone_be.dto.request.property.CreatePropertyRequestDto;
import com.hilmirafiff.airbnb_clone_be.dto.response.property.PropertyResponseDto;
import com.hilmirafiff.airbnb_clone_be.entity.User;
import com.hilmirafiff.airbnb_clone_be.service.AuthService;
import com.hilmirafiff.airbnb_clone_be.util.AppMessageEnum;
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
        GlobalResponseDto<OutputSchemaDataResponseDto<PropertyResponseDto>> response = messageUtils.successDto(propertyService.createProperty(user, createPropertyRequestDto), AppErrorEnum.CREATED);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
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

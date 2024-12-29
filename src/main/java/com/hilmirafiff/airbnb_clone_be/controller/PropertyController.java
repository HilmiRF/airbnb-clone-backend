package com.hilmirafiff.airbnb_clone_be.controller;

import com.hilmirafiff.airbnb_clone_be.dto.GlobalResponseDto;
import com.hilmirafiff.airbnb_clone_be.dto.OutputSchemaDataResponseDto;
import com.hilmirafiff.airbnb_clone_be.dto.request.property.PropertyRequestDto;
import com.hilmirafiff.airbnb_clone_be.dto.response.property.PropertyResponseDto;
import com.hilmirafiff.airbnb_clone_be.entity.User;
import com.hilmirafiff.airbnb_clone_be.service.AuthService;
import com.hilmirafiff.airbnb_clone_be.util.AppMessageEnum;
import com.hilmirafiff.airbnb_clone_be.util.OutputSchemaResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import com.hilmirafiff.airbnb_clone_be.service.PropertyService;
import com.hilmirafiff.airbnb_clone_be.util.AppErrorEnum;
import com.hilmirafiff.airbnb_clone_be.util.MessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

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
    public ResponseEntity<GlobalResponseDto<OutputSchemaDataResponseDto<PropertyResponseDto>>>createProperty(@RequestHeader HttpHeaders requestHeaders, @RequestPart("file") MultipartFile file, @RequestPart PropertyRequestDto propertyRequestDto) throws Exception {
        String token = Objects.requireNonNull(requestHeaders.getFirst(HttpHeaders.AUTHORIZATION)).split(" ")[1];
        User user = this.authService.getUserFromToken(token);
        GlobalResponseDto<OutputSchemaDataResponseDto<PropertyResponseDto>> response = messageUtils.successDto(propertyService.createProperty(user, file, propertyRequestDto), AppErrorEnum.CREATED);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    // Get all properties
    @Operation(summary = "Get all Property")
    @GetMapping
    public ResponseEntity<GlobalResponseDto<OutputSchemaDataResponseDto<List<PropertyResponseDto>>>>getAllProperties() throws Exception {
        GlobalResponseDto<OutputSchemaDataResponseDto<List<PropertyResponseDto>>> responseDto = messageUtils.successWithParamDto(propertyService.getAllProperties(), AppErrorEnum.FETCHED, AppMessageEnum.PROPERTY);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // Get property by id
    @Operation(summary = "Get Property By Id")
    @GetMapping("{property-id}")
    public ResponseEntity<GlobalResponseDto<OutputSchemaDataResponseDto<PropertyResponseDto>>> getPropertyById(@PathVariable(value = "property-id") UUID propertyId) throws Exception {
        GlobalResponseDto<OutputSchemaDataResponseDto<PropertyResponseDto>> responseDto = messageUtils.successWithParamDto(propertyService.getPropertyById(propertyId),AppErrorEnum.FETCHED, AppMessageEnum.PROPERTY);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // TO DO
    // Update Property
    @Operation(summary = "Update Property By Id")
    @PutMapping("{property-id}")
    public ResponseEntity<GlobalResponseDto<OutputSchemaDataResponseDto<PropertyResponseDto>>> updatePropertyById(@PathVariable(value = "property-id") UUID propertyId, @RequestBody PropertyRequestDto propertyRequestDto) throws Exception {
        GlobalResponseDto<OutputSchemaDataResponseDto<PropertyResponseDto>> responseDto = messageUtils.successWithParamDto(propertyService.updatePropertyById(propertyId, propertyRequestDto),AppErrorEnum.UPDATED, AppMessageEnum.PROPERTY);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // Delete Property
    @Operation(summary = "Delete Property By Id")
    @DeleteMapping("{property-id}")
    public ResponseEntity<GlobalResponseDto<OutputSchemaResponseDto>> deletePropertyById(@PathVariable(value = "property-id") UUID propertyId) throws Exception {
        GlobalResponseDto<OutputSchemaResponseDto> responseDto = messageUtils.successWithParamDto(propertyService.deletePropertyById(propertyId), AppErrorEnum.DELETED, AppMessageEnum.PROPERTY);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}

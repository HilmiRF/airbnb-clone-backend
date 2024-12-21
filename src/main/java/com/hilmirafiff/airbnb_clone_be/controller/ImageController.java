package com.hilmirafiff.airbnb_clone_be.controller;

import com.hilmirafiff.airbnb_clone_be.dto.GlobalResponseDto;
import com.hilmirafiff.airbnb_clone_be.dto.OutputSchemaDataResponseDto;
import com.hilmirafiff.airbnb_clone_be.dto.request.image.UserImageRequestDto;
import com.hilmirafiff.airbnb_clone_be.dto.response.image.ImageResponseDto;
import com.hilmirafiff.airbnb_clone_be.entity.Property;
import com.hilmirafiff.airbnb_clone_be.entity.User;
import com.hilmirafiff.airbnb_clone_be.exception.ApplicationWithParamException;
import com.hilmirafiff.airbnb_clone_be.repository.PropertyRepository;
import com.hilmirafiff.airbnb_clone_be.service.AuthService;
import com.hilmirafiff.airbnb_clone_be.service.ImageService;
import com.hilmirafiff.airbnb_clone_be.service.PropertyService;
import com.hilmirafiff.airbnb_clone_be.util.AppErrorEnum;
import com.hilmirafiff.airbnb_clone_be.util.MessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("image")
@Tag(name = "Image", description = "Endpoints for Images")
@Slf4j
public class ImageController {

    private final ImageService imageService;
    private final MessageUtils messageUtils;
    private final AuthService authService;
    private final PropertyRepository propertyRepository;

    public ImageController(ImageService imageService, MessageUtils messageUtils, AuthService authService, PropertyRepository propertyRepository){
        this.imageService = imageService;
        this.messageUtils = messageUtils;
        this.authService = authService;
        this.propertyRepository = propertyRepository;
    }

    @Operation(summary = "Upload property images")
    @PostMapping
    public ResponseEntity<GlobalResponseDto<OutputSchemaDataResponseDto<ImageResponseDto>>> uploadUserImage(@RequestHeader HttpHeaders requestHeaders, @RequestParam("file") MultipartFile file, @RequestParam(value = "property_id", required = false) String propertyId) throws Exception {
        String token = Objects.requireNonNull(requestHeaders.getFirst(HttpHeaders.AUTHORIZATION)).split(" ")[1];
        User user = this.authService.getUserFromToken(token);
        Property property = this.propertyRepository.findById(UUID.fromString(propertyId)).orElseThrow(() -> new ApplicationWithParamException(AppErrorEnum.RESOURCE_NOT_FOUND, "property", null));;
        GlobalResponseDto<OutputSchemaDataResponseDto<ImageResponseDto>> response = messageUtils.successDto(imageService.uploadImages(user, file, property), AppErrorEnum.CREATED);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }
}

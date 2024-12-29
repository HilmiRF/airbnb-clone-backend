package com.hilmirafiff.airbnb_clone_be.service;

import com.hilmirafiff.airbnb_clone_be.dto.OutputSchemaDataResponseDto;
import com.hilmirafiff.airbnb_clone_be.dto.request.property.PropertyRequestDto;
import com.hilmirafiff.airbnb_clone_be.dto.response.property.PropertyResponseDto;
import com.hilmirafiff.airbnb_clone_be.entity.User;
import com.hilmirafiff.airbnb_clone_be.util.OutputSchemaResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface PropertyService {

    OutputSchemaDataResponseDto<List<PropertyResponseDto>> getAllProperties() throws Exception;

    OutputSchemaDataResponseDto<PropertyResponseDto> getPropertyById(UUID propertyId) throws Exception;

    OutputSchemaDataResponseDto<PropertyResponseDto> updatePropertyById(UUID propertyId, PropertyRequestDto propertyRequestDto) throws Exception;

    OutputSchemaResponseDto deletePropertyById(UUID propertyId) throws Exception;

    OutputSchemaDataResponseDto<PropertyResponseDto> createProperty(User user, MultipartFile file, PropertyRequestDto propertyRequestDto) throws Exception;
}

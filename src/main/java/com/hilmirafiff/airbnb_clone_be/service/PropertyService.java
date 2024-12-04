package com.hilmirafiff.airbnb_clone_be.service;

import com.hilmirafiff.airbnb_clone_be.dto.OutputSchemaDataResponseDto;
import com.hilmirafiff.airbnb_clone_be.dto.request.property.CreatePropertyRequestDto;
import com.hilmirafiff.airbnb_clone_be.dto.request.property.PropertyRequestDto;
import com.hilmirafiff.airbnb_clone_be.dto.response.property.PropertyResponseDto;
import com.hilmirafiff.airbnb_clone_be.entity.User;

public interface PropertyService {

    OutputSchemaDataResponseDto<PropertyResponseDto> getAllProperties() throws Exception;

    OutputSchemaDataResponseDto<PropertyResponseDto> getPropertyById(PropertyRequestDto propertyRequestDto) throws Exception;

    OutputSchemaDataResponseDto<PropertyResponseDto> createProperty(User user, CreatePropertyRequestDto createPropertyRequestDto) throws Exception;
}

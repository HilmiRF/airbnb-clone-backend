package com.hilmirafiff.airbnb_clone_be.service.impl;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hilmirafiff.airbnb_clone_be.dto.OutputSchemaDataResponseDto;
import com.hilmirafiff.airbnb_clone_be.dto.request.property.CreatePropertyRequestDto;
import com.hilmirafiff.airbnb_clone_be.dto.request.property.PropertyRequestDto;
import com.hilmirafiff.airbnb_clone_be.dto.response.property.PropertyResponseDto;
import com.hilmirafiff.airbnb_clone_be.entity.Property;
import com.hilmirafiff.airbnb_clone_be.entity.User;
import com.hilmirafiff.airbnb_clone_be.exception.ApplicationWithParamException;
import com.hilmirafiff.airbnb_clone_be.repository.PropertyRepository;
import com.hilmirafiff.airbnb_clone_be.service.PropertyService;
import com.hilmirafiff.airbnb_clone_be.util.AppConstant;
import com.hilmirafiff.airbnb_clone_be.util.AppErrorEnum;
import com.hilmirafiff.airbnb_clone_be.util.AppMessageEnum;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class PropertyServiceImpl implements PropertyService {
    private final PropertyRepository propertyRepository;

    public PropertyServiceImpl(PropertyRepository propertyRepository){
        this.propertyRepository = propertyRepository;
    }
    @Override
    public OutputSchemaDataResponseDto<PropertyResponseDto> getAllProperties() throws Exception {
        return null;
    }

    @Override
    public OutputSchemaDataResponseDto<PropertyResponseDto> getPropertyById(PropertyRequestDto propertyRequestDto) throws Exception {
        return null;
    }

    @Override
    public OutputSchemaDataResponseDto<PropertyResponseDto> createProperty(User user, CreatePropertyRequestDto createPropertyRequestDto) throws Exception {
        boolean isPropertyAlreadyExist = this.propertyRepository.existsByTitleIgnoreCase(createPropertyRequestDto.getTitle());
        if (Boolean.FALSE.equals(isPropertyAlreadyExist)) {
            Property property = new Property();
            property.setId(UUID.randomUUID());
            property.setTitle(createPropertyRequestDto.getTitle());
            property.setDescription(createPropertyRequestDto.getDescription());
            property.setLocation(createPropertyRequestDto.getLocation());
            property.setHostId(user);
            property.setPricePerNight(createPropertyRequestDto.getPricePerNight());

            this.propertyRepository.save(property);
            return OutputSchemaDataResponseDto.<PropertyResponseDto>builder()
                    .status(AppConstant.Status.SUCCESS)
                    .reason(AppMessageEnum.PROPERTY.getMessageEn() + " "+ AppErrorEnum.CREATED.getAppErrorMessageEn())
                    .data(mapToCreatePropertyResponseDto(property))
                    .build();
        } else {
            throw new ApplicationWithParamException(AppErrorEnum.ALREADY_EXISTS, AppMessageEnum.PROPERTY.getMessageEn(), null);
        }
    }
    private PropertyResponseDto mapToCreatePropertyResponseDto(Property property){
        return PropertyResponseDto.builder()
                .id(property.getId())
                .title(property.getTitle())
                .description(property.getDescription())
                .pricePerNight(property.getPricePerNight())
                .location(property.getLocation())
                .hostId(property.getHostId().getUserId().toString())
                .build();
    }
}

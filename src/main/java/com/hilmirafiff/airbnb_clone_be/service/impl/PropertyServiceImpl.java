package com.hilmirafiff.airbnb_clone_be.service.impl;

import com.hilmirafiff.airbnb_clone_be.dto.OutputSchemaDataResponseDto;
import com.hilmirafiff.airbnb_clone_be.dto.request.property.PropertyRequestDto;
import com.hilmirafiff.airbnb_clone_be.dto.response.property.PropertyResponseDto;
import com.hilmirafiff.airbnb_clone_be.entity.Property;
import com.hilmirafiff.airbnb_clone_be.entity.User;
import com.hilmirafiff.airbnb_clone_be.exception.ApplicationException;
import com.hilmirafiff.airbnb_clone_be.exception.ApplicationWithParamException;
import com.hilmirafiff.airbnb_clone_be.repository.PropertyRepository;
import com.hilmirafiff.airbnb_clone_be.service.PropertyService;
import com.hilmirafiff.airbnb_clone_be.util.AppConstant;
import com.hilmirafiff.airbnb_clone_be.util.AppErrorEnum;
import com.hilmirafiff.airbnb_clone_be.util.AppMessageEnum;
import com.hilmirafiff.airbnb_clone_be.util.OutputSchemaResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    public OutputSchemaDataResponseDto<List<PropertyResponseDto>> getAllProperties() throws Exception {
        List<Property> listMenu= this.propertyRepository.findAll();
        return OutputSchemaDataResponseDto.<List<PropertyResponseDto>>builder()
                .status(AppConstant.Status.SUCCESS)
                .reason(AppMessageEnum.PROPERTY.getMessageEn() + " " + AppErrorEnum.FETCHED.getAppErrorMessageEn())
                .data(listMenu.stream()
                        .map(this::mapToPropertyResponseDto)
                        .toList())
                .build();
    }

    @Override
    public OutputSchemaDataResponseDto<PropertyResponseDto> getPropertyById(UUID propertyId) throws Exception {
        try{
            Property property = this.propertyRepository.findById(propertyId).orElseThrow(() -> new ApplicationWithParamException(AppErrorEnum.RESOURCE_NOT_FOUND, "property", null));
            return OutputSchemaDataResponseDto.<PropertyResponseDto>builder()
                    .status(AppConstant.Status.SUCCESS)
                    .reason(AppMessageEnum.PROPERTY.getMessageEn() + AppErrorEnum.FETCHED.getAppErrorMessageEn())
                    .data(mapToPropertyResponseDto(property))
                    .build();
        } catch (Exception ex){
            throw new ApplicationWithParamException(AppErrorEnum.RESOURCE_NOT_FOUND, "property", null);
        }

    }

    @Override
    public OutputSchemaDataResponseDto<PropertyResponseDto> updatePropertyById(UUID propertyId, PropertyRequestDto propertyRequestDto) throws Exception {
        try{
            Property property = this.propertyRepository.findById(propertyId).orElseThrow(() -> new ApplicationWithParamException(AppErrorEnum.RESOURCE_NOT_FOUND, "property", null));
            property.setTitle(propertyRequestDto.getTitle());
            property.setDescription(propertyRequestDto.getDescription());
            property.setPricePerNight(propertyRequestDto.getPricePerNight());
            property.setLocation(propertyRequestDto.getLocation());

            this.propertyRepository.save(property);

            return OutputSchemaDataResponseDto.<PropertyResponseDto>builder()
                    .status(AppConstant.Status.SUCCESS)
                    .reason(AppMessageEnum.PROPERTY.getMessageEn() + " " + AppErrorEnum.UPDATED.getAppErrorMessageEn())
                    .data(mapToPropertyResponseDto(property))
                    .build();
        } catch (Exception ex){
            throw new ApplicationWithParamException(AppErrorEnum.ALREADY_EXISTS, AppMessageEnum.PROPERTY.getMessageEn(), null);
        }
    }

    @Override
    public OutputSchemaResponseDto deletePropertyById(UUID propertyId) throws Exception {
        try{
            Property property = this.propertyRepository.findById(propertyId).orElseThrow(() -> new ApplicationWithParamException(AppErrorEnum.RESOURCE_NOT_FOUND, "property", null));
            this.propertyRepository.deleteById(propertyId);

            return OutputSchemaResponseDto.builder()
                    .status(AppConstant.Status.SUCCESS)
                    .reason(AppMessageEnum.PROPERTY.getMessageEn()+" "+AppErrorEnum.DELETED)
                    .build();
        } catch (Exception ex){
            throw new ApplicationException(AppErrorEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public OutputSchemaDataResponseDto<PropertyResponseDto> createProperty(User user, PropertyRequestDto propertyRequestDto) throws Exception {
        boolean isPropertyAlreadyExist = this.propertyRepository.existsByTitleIgnoreCase(propertyRequestDto.getTitle());
        if (Boolean.FALSE.equals(isPropertyAlreadyExist)) {
            Property property = new Property();
            property.setId(UUID.randomUUID());
            property.setTitle(propertyRequestDto.getTitle());
            property.setDescription(propertyRequestDto.getDescription());
            property.setLocation(propertyRequestDto.getLocation());
            property.setHostId(user);
            property.setPricePerNight(propertyRequestDto.getPricePerNight());

            this.propertyRepository.save(property);
            return OutputSchemaDataResponseDto.<PropertyResponseDto>builder()
                    .status(AppConstant.Status.SUCCESS)
                    .reason(AppMessageEnum.PROPERTY.getMessageEn() + " "+ AppErrorEnum.CREATED.getAppErrorMessageEn())
                    .data(mapToPropertyResponseDto(property))
                    .build();
        } else {
            throw new ApplicationWithParamException(AppErrorEnum.ALREADY_EXISTS, AppMessageEnum.PROPERTY.getMessageEn(), null);
        }
    }
    private PropertyResponseDto mapToPropertyResponseDto(Property property){
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

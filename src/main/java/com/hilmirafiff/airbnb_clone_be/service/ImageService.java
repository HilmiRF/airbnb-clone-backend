package com.hilmirafiff.airbnb_clone_be.service;

import com.hilmirafiff.airbnb_clone_be.dto.OutputSchemaDataResponseDto;
import com.hilmirafiff.airbnb_clone_be.dto.response.image.ImageResponseDto;
import com.hilmirafiff.airbnb_clone_be.entity.Property;
import com.hilmirafiff.airbnb_clone_be.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    OutputSchemaDataResponseDto<ImageResponseDto> uploadImages(User user, MultipartFile file, Property property) throws Exception;

    OutputSchemaDataResponseDto<List<ImageResponseDto>> getPropertyImage(Property property) throws Exception;
}

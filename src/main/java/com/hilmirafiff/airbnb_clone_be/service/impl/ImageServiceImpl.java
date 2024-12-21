package com.hilmirafiff.airbnb_clone_be.service.impl;

import com.hilmirafiff.airbnb_clone_be.dto.OutputSchemaDataResponseDto;
import com.hilmirafiff.airbnb_clone_be.dto.response.booking.BookingResponseDto;
import com.hilmirafiff.airbnb_clone_be.dto.response.image.ImageResponseDto;
import com.hilmirafiff.airbnb_clone_be.entity.Image;
import com.hilmirafiff.airbnb_clone_be.entity.Property;
import com.hilmirafiff.airbnb_clone_be.entity.User;
import com.hilmirafiff.airbnb_clone_be.exception.ApplicationException;
import com.hilmirafiff.airbnb_clone_be.exception.ApplicationWithParamException;
import com.hilmirafiff.airbnb_clone_be.repository.ImageRepository;
import com.hilmirafiff.airbnb_clone_be.service.ImageService;
import com.hilmirafiff.airbnb_clone_be.util.AppConstant;
import com.hilmirafiff.airbnb_clone_be.util.AppErrorEnum;
import com.hilmirafiff.airbnb_clone_be.util.AppMessageEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    @Value("${supabase.url}")
    private String supabaseUrl;
    @Value("${supabase.apikey}")
    private String supabaseApiKey;
    private final ImageRepository imageRepository;

    public ImageServiceImpl(ImageRepository imageRepository){
        this.imageRepository = imageRepository;
    }
    @Override
    public OutputSchemaDataResponseDto<ImageResponseDto> uploadImages(User user, MultipartFile file, Property property) throws Exception {
        String bucketName = "images";
        String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
        try{
            // Upload file to Supabase
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(supabaseUrl + "/storage/v1/object/" + bucketName + "/" + fileName))
                    .header("Authorization", "Bearer " + supabaseApiKey)
                    .header("Content-Type", file.getContentType())
                    .POST(HttpRequest.BodyPublishers.ofByteArray(file.getBytes()))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200 && response.statusCode() != 201) {
                throw new ApplicationException(AppErrorEnum.INTERNAL_SERVER_ERROR);
            }

            // Save metadata in the database
            Image image = new Image();
            image.setUrl(supabaseUrl + "/storage/v1/object/" + bucketName + "/" + fileName);
            image.setFileName(fileName);
            image.setUploadedAt(LocalDateTime.now());
            image.setUserId(user);
            image.setPropertyId(property);
            imageRepository.save(image);

            return OutputSchemaDataResponseDto.<ImageResponseDto>builder()
                    .status(AppConstant.Status.SUCCESS)
                    .reason(AppMessageEnum.IMAGE.getMessageEn() + " " + AppErrorEnum.CREATED.getAppErrorMessageEn())
                    .data(mapToImageResponseDto(image))
                    .build();
        } catch (Exception e){
            throw new ApplicationException(AppErrorEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public OutputSchemaDataResponseDto<List<ImageResponseDto>> getPropertyImage(Property property) throws Exception {
        try{
            List<Image> images = this.imageRepository.findByPropertyId(property.getId());
            return OutputSchemaDataResponseDto.<List<ImageResponseDto>>builder()
                    .status(AppConstant.Status.SUCCESS)
                    .reason(AppMessageEnum.IMAGE.getMessageEn() + " " + AppErrorEnum.FETCHED.getAppErrorMessageEn())
                    .data(images.stream()
                            .map(this::mapToImageResponseDto)
                            .toList())
                    .build();
        } catch (Exception e){
            throw new ApplicationException(AppErrorEnum.INTERNAL_SERVER_ERROR);
        }
    }

    private ImageResponseDto mapToImageResponseDto(Image image){
        return ImageResponseDto.builder()
                .id(image.getId())
                .url(image.getUrl())
                .fileName(image.getFileName())
                .uploadedAt(image.getUploadedAt())
                .userId(image.getUserId().getUserId().toString())
                .propertyId(image.getPropertyId().getId().toString())
                .build();
    }
}

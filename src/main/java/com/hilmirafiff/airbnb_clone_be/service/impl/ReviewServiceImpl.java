package com.hilmirafiff.airbnb_clone_be.service.impl;

import com.hilmirafiff.airbnb_clone_be.dto.OutputSchemaDataResponseDto;
import com.hilmirafiff.airbnb_clone_be.dto.request.review.ReviewRequestDto;
import com.hilmirafiff.airbnb_clone_be.dto.response.review.ReviewResponseDto;
import com.hilmirafiff.airbnb_clone_be.entity.Property;
import com.hilmirafiff.airbnb_clone_be.entity.Review;
import com.hilmirafiff.airbnb_clone_be.entity.User;
import com.hilmirafiff.airbnb_clone_be.exception.ApplicationException;
import com.hilmirafiff.airbnb_clone_be.exception.ApplicationWithParamException;
import com.hilmirafiff.airbnb_clone_be.repository.PropertyRepository;
import com.hilmirafiff.airbnb_clone_be.repository.ReviewRepository;
import com.hilmirafiff.airbnb_clone_be.service.ReviewService;
import com.hilmirafiff.airbnb_clone_be.util.AppConstant;
import com.hilmirafiff.airbnb_clone_be.util.AppErrorEnum;
import com.hilmirafiff.airbnb_clone_be.util.AppMessageEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final PropertyRepository propertyRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, PropertyRepository propertyRepository) {
        this.reviewRepository = reviewRepository;
        this.propertyRepository = propertyRepository;
    }


    @Override
    public OutputSchemaDataResponseDto<ReviewResponseDto> createReview(User user, ReviewRequestDto reviewRequestDto) throws Exception {
        Property property = this.propertyRepository.findById(UUID.fromString(reviewRequestDto.getPropertyId())).orElseThrow(() -> new ApplicationWithParamException(AppErrorEnum.RESOURCE_NOT_FOUND, "property", null));
        try{
            Review review = new Review();
            review.setId(UUID.randomUUID());
            review.setUserId(user);
            review.setComment(reviewRequestDto.getComment());
            review.setRating(reviewRequestDto.getRating());
            review.setPropertyId(property);

            reviewRepository.save(review);
            return OutputSchemaDataResponseDto.<ReviewResponseDto>builder()
                    .status(AppConstant.Status.SUCCESS)
                    .reason(AppMessageEnum.REVIEW.getMessageEn() + " " + AppErrorEnum.CREATED.getAppErrorMessageEn())
                    .data(mapToReviewResponseDto(review))
                    .build();
        } catch (Exception ex){
            throw new ApplicationException(AppErrorEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public OutputSchemaDataResponseDto<List<ReviewResponseDto>> getAllReview(UUID propertyId) throws Exception {
        List<Review> reviews = this.reviewRepository.findByPropertyId(propertyId);
        return OutputSchemaDataResponseDto.<List<ReviewResponseDto>>builder()
                .status(AppConstant.Status.SUCCESS)
                .reason(AppMessageEnum.REVIEW.getMessageEn() + " " + AppErrorEnum.FETCHED.getAppErrorMessageEn())
                .data(reviews.stream()
                        .map(this::mapToReviewResponseDto)
                        .toList())
                .build();
    }

    public ReviewResponseDto mapToReviewResponseDto(Review review){
        return ReviewResponseDto.builder()
                .id(review.getId())
                .comment(review.getComment())
                .rating(review.getRating())
                .propertyId(review.getPropertyId().getId().toString())
                .userId(review.getUserId().getUserId().toString())
                .build();
    }
}

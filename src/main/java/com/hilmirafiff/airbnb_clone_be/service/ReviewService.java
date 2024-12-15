package com.hilmirafiff.airbnb_clone_be.service;

import com.hilmirafiff.airbnb_clone_be.dto.OutputSchemaDataResponseDto;
import com.hilmirafiff.airbnb_clone_be.dto.request.review.ReviewRequestDto;
import com.hilmirafiff.airbnb_clone_be.dto.response.review.ReviewResponseDto;
import com.hilmirafiff.airbnb_clone_be.entity.User;

import java.util.List;
import java.util.UUID;

public interface ReviewService {
    OutputSchemaDataResponseDto<ReviewResponseDto> createReview(User user, ReviewRequestDto reviewRequestDto) throws Exception;
    OutputSchemaDataResponseDto<List<ReviewResponseDto>> getAllReview(UUID propertyId) throws Exception;

}

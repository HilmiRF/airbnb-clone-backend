package com.hilmirafiff.airbnb_clone_be.controller;

import com.hilmirafiff.airbnb_clone_be.dto.GlobalResponseDto;
import com.hilmirafiff.airbnb_clone_be.dto.OutputSchemaDataResponseDto;
import com.hilmirafiff.airbnb_clone_be.dto.request.review.ReviewRequestDto;
import com.hilmirafiff.airbnb_clone_be.dto.response.review.ReviewResponseDto;
import com.hilmirafiff.airbnb_clone_be.entity.User;
import com.hilmirafiff.airbnb_clone_be.service.AuthService;
import com.hilmirafiff.airbnb_clone_be.service.ReviewService;
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

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("review")
@Tag(name = "Review", description = "Endpoints for Review")
@Slf4j
public class ReviewController {
    private final ReviewService reviewService;
    private final MessageUtils messageUtils;
    private final AuthService authService;

    public ReviewController(ReviewService reviewService, MessageUtils messageUtils, AuthService authService) {
        this.reviewService = reviewService;
        this.messageUtils = messageUtils;
        this.authService = authService;
    }

    // Create new Review
    @Operation(summary = "Create a Review")
    @PostMapping
    public ResponseEntity<GlobalResponseDto<OutputSchemaDataResponseDto<ReviewResponseDto>>> CreateBooking(@RequestHeader HttpHeaders requestHeaders, @Valid @RequestBody ReviewRequestDto reviewRequestDto) throws Exception {
        String token = Objects.requireNonNull(requestHeaders.getFirst(HttpHeaders.AUTHORIZATION)).split(" ")[1];
        User user = this.authService.getUserFromToken(token);
        GlobalResponseDto<OutputSchemaDataResponseDto<ReviewResponseDto>> response = messageUtils.successDto(reviewService.createReview(user, reviewRequestDto), AppErrorEnum.CREATED);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Get all Review for specific property
    @Operation(summary = "Get all Review")
    @GetMapping("/property/{property-id}")
    public ResponseEntity<GlobalResponseDto<OutputSchemaDataResponseDto<List<ReviewResponseDto>>>> listAllReview(@PathVariable(value = "property-id") UUID propertyId) throws Exception {
        GlobalResponseDto<OutputSchemaDataResponseDto<List<ReviewResponseDto>>> response = messageUtils.successDto(reviewService.getAllReview(propertyId), AppErrorEnum.CREATED);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

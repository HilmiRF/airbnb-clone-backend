package com.hilmirafiff.airbnb_clone_be.controller;

import com.hilmirafiff.airbnb_clone_be.dto.GlobalResponseDto;
import com.hilmirafiff.airbnb_clone_be.dto.OutputSchemaDataResponseDto;
import com.hilmirafiff.airbnb_clone_be.dto.request.booking.BookingRequestDto;
import com.hilmirafiff.airbnb_clone_be.dto.response.booking.BookingResponseDto;
import com.hilmirafiff.airbnb_clone_be.entity.User;
import com.hilmirafiff.airbnb_clone_be.repository.PropertyRepository;
import com.hilmirafiff.airbnb_clone_be.service.AuthService;
import com.hilmirafiff.airbnb_clone_be.service.BookingService;
import com.hilmirafiff.airbnb_clone_be.util.AppErrorEnum;
import com.hilmirafiff.airbnb_clone_be.util.AppMessageEnum;
import com.hilmirafiff.airbnb_clone_be.util.MessageUtils;
import com.hilmirafiff.airbnb_clone_be.util.OutputSchemaResponseDto;
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
@RequestMapping("booking")
@Tag(name = "Booking", description = "Endpoints for Booking")
@Slf4j
public class BookingController {
    private final BookingService bookingService;
    private final MessageUtils messageUtils;
    private final AuthService authService;

    public BookingController(BookingService bookingService, MessageUtils messageUtils, AuthService authService) {
        this.bookingService = bookingService;
        this.messageUtils = messageUtils;
        this.authService = authService;
    }

    // Create new Booking
    @Operation(summary = "Create a Booking")
    @PostMapping
    public ResponseEntity<GlobalResponseDto<OutputSchemaDataResponseDto<BookingResponseDto>>> CreateBooking(@RequestHeader HttpHeaders requestHeaders, @Valid @RequestBody BookingRequestDto bookingRequestDto) throws Exception {
        String token = Objects.requireNonNull(requestHeaders.getFirst(HttpHeaders.AUTHORIZATION)).split(" ")[1];
        User user = this.authService.getUserFromToken(token);
        GlobalResponseDto<OutputSchemaDataResponseDto<BookingResponseDto>> response = messageUtils.successDto(bookingService.createBooking(user, bookingRequestDto), AppErrorEnum.CREATED);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Get all Bookings for a User
    @Operation(summary = "Get all Bookings for a User")
    @GetMapping
    public ResponseEntity<GlobalResponseDto<OutputSchemaDataResponseDto<List<BookingResponseDto>>>>getAllBookings(@RequestHeader HttpHeaders requestHeaders) throws Exception {
        String token = Objects.requireNonNull(requestHeaders.getFirst(HttpHeaders.AUTHORIZATION)).split(" ")[1];
        User user = this.authService.getUserFromToken(token);
        GlobalResponseDto<OutputSchemaDataResponseDto<List<BookingResponseDto>>> responseDto = messageUtils.successWithParamDto(bookingService.getAllBookingsForUser(user), AppErrorEnum.FETCHED, AppMessageEnum.BOOKING);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // Get Booking by id
    @Operation(summary = "Get Booking By Id")
    @GetMapping("{booking-id}")
    public ResponseEntity<GlobalResponseDto<OutputSchemaDataResponseDto<BookingResponseDto>>> getBookingById(@PathVariable(value = "booking-id") UUID bookingId) throws Exception {
        GlobalResponseDto<OutputSchemaDataResponseDto<BookingResponseDto>> responseDto = messageUtils.successWithParamDto(bookingService.getBookingById(bookingId),AppErrorEnum.FETCHED, AppMessageEnum.BOOKING);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // Update Booking
    @Operation(summary = "Update Booking By Id")
    @PutMapping("{booking-id}")
    public ResponseEntity<GlobalResponseDto<OutputSchemaDataResponseDto<BookingResponseDto>>> updateBookingById(@PathVariable(value = "booking-id") UUID bookingId, @RequestBody BookingRequestDto bookingRequestDto) throws Exception {
        GlobalResponseDto<OutputSchemaDataResponseDto<BookingResponseDto>> responseDto = messageUtils.successWithParamDto(bookingService.updateBookingById(bookingId, bookingRequestDto), AppErrorEnum.UPDATED, AppMessageEnum.BOOKING);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // Delete Booking
    @Operation(summary = "Delete Booking By Id")
    @DeleteMapping("{booking-id}")
    public ResponseEntity<GlobalResponseDto<OutputSchemaResponseDto>> deleteBookingById(@PathVariable(value = "booking-id") UUID bookingId) throws Exception {
        GlobalResponseDto<OutputSchemaResponseDto> responseDto = messageUtils.successWithParamDto(bookingService.deleteBookingById(bookingId), AppErrorEnum.DELETED, AppMessageEnum.BOOKING);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}

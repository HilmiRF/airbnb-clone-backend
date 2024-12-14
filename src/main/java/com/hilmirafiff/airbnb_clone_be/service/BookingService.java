package com.hilmirafiff.airbnb_clone_be.service;

import com.hilmirafiff.airbnb_clone_be.dto.OutputSchemaDataResponseDto;
import com.hilmirafiff.airbnb_clone_be.dto.request.booking.BookingRequestDto;
import com.hilmirafiff.airbnb_clone_be.dto.response.booking.BookingResponseDto;
import com.hilmirafiff.airbnb_clone_be.entity.Property;
import com.hilmirafiff.airbnb_clone_be.entity.User;
import com.hilmirafiff.airbnb_clone_be.util.OutputSchemaResponseDto;

import java.util.List;
import java.util.UUID;

public interface BookingService {
    OutputSchemaDataResponseDto<List<BookingResponseDto>> getAllBookingsForUser(User user) throws Exception;

    OutputSchemaDataResponseDto<BookingResponseDto> getBookingById(UUID bookingId) throws Exception;

    OutputSchemaDataResponseDto<BookingResponseDto> updateBookingById(UUID bookingId, BookingRequestDto bookingRequestDto) throws Exception;

    OutputSchemaResponseDto deleteBookingById(UUID bookingId) throws Exception;

    OutputSchemaDataResponseDto<BookingResponseDto> createBooking(User user, BookingRequestDto bookingRequestDto) throws Exception;
}

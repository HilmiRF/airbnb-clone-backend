package com.hilmirafiff.airbnb_clone_be.service.impl;

import com.hilmirafiff.airbnb_clone_be.dto.OutputSchemaDataResponseDto;
import com.hilmirafiff.airbnb_clone_be.dto.request.booking.BookingRequestDto;
import com.hilmirafiff.airbnb_clone_be.dto.response.booking.BookingResponseDto;
import com.hilmirafiff.airbnb_clone_be.entity.Booking;
import com.hilmirafiff.airbnb_clone_be.entity.Property;
import com.hilmirafiff.airbnb_clone_be.entity.User;
import com.hilmirafiff.airbnb_clone_be.exception.ApplicationException;
import com.hilmirafiff.airbnb_clone_be.exception.ApplicationWithParamException;
import com.hilmirafiff.airbnb_clone_be.repository.BookingRepository;
import com.hilmirafiff.airbnb_clone_be.repository.PropertyRepository;
import com.hilmirafiff.airbnb_clone_be.service.BookingService;
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
public class BookingServiceImpl implements BookingService {

    private BookingRepository bookingRepository;

    private PropertyRepository propertyRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, PropertyRepository propertyRepository) {
        this.bookingRepository = bookingRepository;
        this.propertyRepository = propertyRepository;
    }

    @Override
    public OutputSchemaDataResponseDto<BookingResponseDto> createBooking(User user, BookingRequestDto bookingRequestDto) throws Exception {
        Property property = this.propertyRepository.findById(UUID.fromString(bookingRequestDto.getProperty())).orElseThrow(() -> new ApplicationWithParamException(AppErrorEnum.RESOURCE_NOT_FOUND, "property", null));
        if (property != null) {
            Booking booking = new Booking();
            booking.setId(UUID.randomUUID());
            booking.setProperty(property);
            booking.setUserId(user);
            booking.setStartDate(bookingRequestDto.getStartDate());
            booking.setEndDate(bookingRequestDto.getEndDate());
            booking.setTotalPrice(bookingRequestDto.getTotalPrice());
            bookingRepository.save(booking);

            return OutputSchemaDataResponseDto.<BookingResponseDto>builder()
                    .status(AppConstant.Status.SUCCESS)
                    .reason(AppMessageEnum.BOOKING.getMessageEn() + " "+ AppErrorEnum.CREATED.getAppErrorMessageEn())
                    .data(mapToBookingResponseDto(booking))
                    .build();
        } else {
            throw new ApplicationWithParamException(AppErrorEnum.ALREADY_EXISTS, AppMessageEnum.PROPERTY.getMessageEn(), null);
        }
    }

    @Override
    public OutputSchemaDataResponseDto<List<BookingResponseDto>> getAllBookingsForUser(User user) throws Exception {
        return null;
    }

    @Override
    public OutputSchemaDataResponseDto<BookingResponseDto> getBookingById(UUID bookingId) throws Exception {
        try{
            Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new ApplicationWithParamException(AppErrorEnum.RESOURCE_NOT_FOUND, "booking", null));
            return OutputSchemaDataResponseDto.<BookingResponseDto>builder()
                    .status(AppConstant.Status.SUCCESS)
                    .reason(AppMessageEnum.BOOKING.getMessageEn() + " " + AppErrorEnum.FETCHED.getAppErrorMessageEn())
                    .data(mapToBookingResponseDto(booking))
                    .build();
        } catch (Exception ex){
            throw new ApplicationException(AppErrorEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public OutputSchemaDataResponseDto<BookingResponseDto> updateBookingById(UUID bookingId, BookingRequestDto bookingRequestDto) throws Exception {
        return null;
    }

    @Override
    public OutputSchemaResponseDto deleteBookingById(UUID bookingId) throws Exception {
        try{
            Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new ApplicationWithParamException(AppErrorEnum.RESOURCE_NOT_FOUND, "booking", null));
            bookingRepository.delete(booking);
            return OutputSchemaResponseDto.builder()
                    .status(AppConstant.Status.SUCCESS)
                    .reason(AppMessageEnum.BOOKING.getMessageEn() + " " + AppErrorEnum.DELETED)
                    .build();
        } catch (Exception ex){
            throw new ApplicationException(AppErrorEnum.INTERNAL_SERVER_ERROR);
        }
    }

    private BookingResponseDto mapToBookingResponseDto(Booking booking){
        return BookingResponseDto.builder()
                .id(booking.getId())
                .property(booking.getProperty().getId().toString())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .totalPrice(booking.getTotalPrice())
                .userId(booking.getUserId().getUserId().toString())
                .build();
    }
}

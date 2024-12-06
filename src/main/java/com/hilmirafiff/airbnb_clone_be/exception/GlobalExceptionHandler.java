package com.hilmirafiff.airbnb_clone_be.exception;

import com.hilmirafiff.airbnb_clone_be.dto.GlobalResponseDto;
import com.hilmirafiff.airbnb_clone_be.util.AppErrorEnum;
import com.hilmirafiff.airbnb_clone_be.util.MessageUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final MessageUtils messageUtils;

    public GlobalExceptionHandler(MessageUtils messageUtils) {
        this.messageUtils = messageUtils;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalResponseDto<Object>> handleGlobalException(Exception ex) {
        GlobalResponseDto<Object> responseDto = messageUtils.globalExceptionDto(ex);
        return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<GlobalResponseDto<Object>> handleApplicationException(ApplicationException ex) {
        GlobalResponseDto<Object> responseDto = messageUtils.applicationExceptionDto(ex);
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApplicationWithParamException.class)
    public ResponseEntity<GlobalResponseDto<Object>> handleApplicationWithParamException(ApplicationWithParamException ex) {
        GlobalResponseDto<Object> responseDto = messageUtils.applicationWithParamExceptionDto(ex);
        if (responseDto.getErrorSchema().getErrorCode().equals(AppErrorEnum.RESOURCE_NOT_FOUND.getAppErrorCode())) {
            return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<GlobalResponseDto<Object>> handleBadCredentialsException() {
        GlobalResponseDto<Object> responseDto = messageUtils.badCredentialsExceptionDto();
        return new ResponseEntity<>(responseDto, HttpStatus.UNAUTHORIZED);
    }
}

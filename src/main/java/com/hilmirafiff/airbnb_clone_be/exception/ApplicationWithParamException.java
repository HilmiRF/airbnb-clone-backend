package com.hilmirafiff.airbnb_clone_be.exception;

import com.hilmirafiff.airbnb_clone_be.util.AppErrorEnum;
import lombok.Getter;

@Getter
public class ApplicationWithParamException extends RuntimeException {
    private final AppErrorEnum appErrorEnum;
    private final String fieldName;
    private final Object responseDto;

    public ApplicationWithParamException(AppErrorEnum appErrorEnum, String fieldName, Object responseDto) {
        this.appErrorEnum = appErrorEnum;
        this.fieldName = fieldName;
        this.responseDto = responseDto;
    }
}


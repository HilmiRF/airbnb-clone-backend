package com.hilmirafiff.airbnb_clone_be.exception;

import com.hilmirafiff.airbnb_clone_be.util.AppErrorEnum;
import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException{
    private final AppErrorEnum appErrorEnum;

    public ApplicationException(AppErrorEnum appErrorEnum) {
        this.appErrorEnum = appErrorEnum;
    }
}


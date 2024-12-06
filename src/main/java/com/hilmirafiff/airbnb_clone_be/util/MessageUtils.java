package com.hilmirafiff.airbnb_clone_be.util;

import com.hilmirafiff.airbnb_clone_be.dto.GlobalResponseDto;
import com.hilmirafiff.airbnb_clone_be.exception.ApplicationException;
import com.hilmirafiff.airbnb_clone_be.exception.ApplicationWithParamException;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Objects;

@Component
public class MessageUtils {

    public <T> GlobalResponseDto<T> successDto(T data, AppErrorEnum appErrorEnum) {
        return new GlobalResponseDto<>(GlobalResponseDto.ErrorSchema.builder()
                .errorCode(appErrorEnum.getAppErrorCode())
                .errorMessage(GlobalResponseDto.ErrorMessage.builder()
                        .indonesian(appErrorEnum.getAppErrorMessageInd())
                        .english(appErrorEnum.getAppErrorMessageEn())
                        .build())
                .build(), data);
    }

    public <T> GlobalResponseDto<T> alreadyExistDto(T data, AppErrorEnum appErrorEnum, AppMessageEnum appMessageEnum, String argument) {
        return new GlobalResponseDto<>(GlobalResponseDto.ErrorSchema.builder()
                .errorCode(appErrorEnum.getAppErrorCode())
                .errorMessage(GlobalResponseDto.ErrorMessage.builder()
                        .indonesian(MessageFormat.format(AppMessageEnum.PROPERTY.getMessageInd()+ " " + AppErrorEnum.ALREADY_EXISTS.getAppErrorMessageInd(), argument))
                        .english(MessageFormat.format(AppMessageEnum.PROPERTY.getMessageEn()+ " " + AppErrorEnum.ALREADY_EXISTS.getAppErrorMessageEn(), argument))
                        .build())
                .build(), data);
    }

    public  <T> GlobalResponseDto<T> successWithParamDto(T data, AppErrorEnum appErrorEnum, AppMessageEnum appMessageEnum) {
        return new GlobalResponseDto<>(GlobalResponseDto.ErrorSchema.builder()
                .errorCode(appErrorEnum.getAppErrorCode())
                .errorMessage(GlobalResponseDto.ErrorMessage.builder()
                        .indonesian(appMessageEnum.getMessageInd() + " " +appErrorEnum.getAppErrorMessageInd())
                        .english(appMessageEnum.getMessageEn() + " " +appErrorEnum.getAppErrorMessageEn())
                        .build())
                .build(), data);
    }

    public GlobalResponseDto<Object> globalExceptionDto(Exception ex) {
        OutputSchemaResponseDto errorDto = generateErrorResponseDto(ex.getMessage());
        return GlobalResponseDto.builder()
                .errorSchema(GlobalResponseDto.ErrorSchema.builder()
                        .errorCode(AppErrorEnum.INTERNAL_SERVER_ERROR.getAppErrorCode())
                        .errorMessage(GlobalResponseDto.ErrorMessage.builder()
                                .indonesian(AppErrorEnum.INTERNAL_SERVER_ERROR.getAppErrorMessageInd())
                                .english(AppErrorEnum.INTERNAL_SERVER_ERROR.getAppErrorMessageEn())
                                .build())
                        .build())
                .outputSchema(errorDto)
                .build();
    }

    private OutputSchemaResponseDto generateErrorResponseDto(String message) {
        return OutputSchemaResponseDto.builder()
                .status(AppConstant.Status.ERROR)
                .reason(message)
                .build();
    }

    public GlobalResponseDto<Object> applicationExceptionDto(ApplicationException ex) {
        OutputSchemaResponseDto errorDto = generateErrorResponseDto(ex.getAppErrorEnum().getAppErrorMessageEn());
        return GlobalResponseDto.builder()
                .errorSchema(GlobalResponseDto.ErrorSchema.builder()
                        .errorCode(ex.getAppErrorEnum().getAppErrorCode())
                        .errorMessage(GlobalResponseDto.ErrorMessage.builder()
                                .indonesian(ex.getAppErrorEnum().getAppErrorMessageInd())
                                .english(ex.getAppErrorEnum().getAppErrorMessageEn())
                                .build())
                        .build())
                .outputSchema(errorDto)
                .build();
    }

    public GlobalResponseDto<Object> applicationWithParamExceptionDto(ApplicationWithParamException ex) {
        return GlobalResponseDto.builder()
                .errorSchema(GlobalResponseDto.ErrorSchema.builder()
                        .errorCode(ex.getAppErrorEnum().getAppErrorCode())
                        .errorMessage(GlobalResponseDto.ErrorMessage.builder()
                                .indonesian(ex.getAppErrorEnum().getAppErrorMessageInd().replace("{0}", ex.getFieldName()))
                                .english(ex.getAppErrorEnum().getAppErrorMessageEn().replace("{0}", ex.getFieldName()))
                                .build())
                        .build())
                .outputSchema(ex.getResponseDto())
                .build();
    }

    public GlobalResponseDto<Object> badCredentialsExceptionDto() {
        return GlobalResponseDto.builder()
                .errorSchema(GlobalResponseDto.ErrorSchema.builder()
                        .errorCode(AppErrorEnum.WRONG_USERNAME_OR_PASSWORD.getAppErrorCode())
                        .errorMessage(GlobalResponseDto.ErrorMessage.builder()
                                .indonesian(AppErrorEnum.WRONG_USERNAME_OR_PASSWORD.getAppErrorMessageInd())
                                .english(AppErrorEnum.WRONG_USERNAME_OR_PASSWORD.getAppErrorMessageEn())
                                .build())
                        .build())
                .build();
    }
}


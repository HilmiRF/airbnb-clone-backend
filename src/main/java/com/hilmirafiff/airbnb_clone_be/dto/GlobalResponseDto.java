package com.hilmirafiff.airbnb_clone_be.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GlobalResponseDto<T> {
    private ErrorSchema errorSchema;
    private T outputSchema;

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class ErrorSchema {
        private String errorCode;
        private ErrorMessage errorMessage;
    }

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ErrorMessage {
        private Object indonesian;
        private Object english;
    }
}


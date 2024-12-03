package com.hilmirafiff.airbnb_clone_be.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutputSchemaDataResponseDto<T>{
    private String status;
    private String reason;
    private T data;

}


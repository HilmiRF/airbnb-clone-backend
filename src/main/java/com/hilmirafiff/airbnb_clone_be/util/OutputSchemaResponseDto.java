package com.hilmirafiff.airbnb_clone_be.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutputSchemaResponseDto {
    private String status;
    private String reason;
}


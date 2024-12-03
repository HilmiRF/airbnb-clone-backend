package com.hilmirafiff.airbnb_clone_be.dto.response.userlevel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLevelResponseDto {
    private Long id;

    private String title;

    private String description;

    private String flag;
}

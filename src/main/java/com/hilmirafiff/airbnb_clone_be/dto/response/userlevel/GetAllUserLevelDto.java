package com.hilmirafiff.airbnb_clone_be.dto.response.userlevel;

import com.hilmirafiff.airbnb_clone_be.util.UserLevelLayerEnum;
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
public class GetAllUserLevelDto {
    private Long id;

    private String title;

    private Long totalMember;

    private Long flagId;

    private UserLevelLayerEnum layer;
}


package com.hilmirafiff.airbnb_clone_be.service;

import com.hilmirafiff.airbnb_clone_be.dto.OutputSchemaDataResponseDto;
import com.hilmirafiff.airbnb_clone_be.dto.request.userlevel.UpsertUserLevelRequestDto;
import com.hilmirafiff.airbnb_clone_be.dto.response.userlevel.GetAllUserLevelDto;
import com.hilmirafiff.airbnb_clone_be.dto.response.userlevel.GetUserLevelByIdResponseDto;
import com.hilmirafiff.airbnb_clone_be.dto.response.userlevel.UserLevelResponseDto;
import com.hilmirafiff.airbnb_clone_be.entity.User;
import com.hilmirafiff.airbnb_clone_be.entity.UserLevel;

import java.util.List;
import java.util.UUID;

public interface UserLevelService {
    UserLevel getUserLevelById (UUID id);

    OutputSchemaDataResponseDto<GetUserLevelByIdResponseDto> getUserLevelDtoById(Long id);

    UserLevel getUserLevelDtoById(UUID id);

    OutputSchemaDataResponseDto<List<GetAllUserLevelDto>> getAllUserLevel();

    OutputSchemaDataResponseDto<UserLevelResponseDto> createUserLevel(User user, UpsertUserLevelRequestDto req);

    OutputSchemaDataResponseDto<UserLevelResponseDto> updateUserLevel(User user, Long userLevelId, UpsertUserLevelRequestDto req);

    OutputSchemaDataResponseDto<UserLevelResponseDto> deleteUserLevel(User user, Long userLevelId);

    OutputSchemaDataResponseDto<UserLevelResponseDto> activateUserLevel(User user, Long userLevelId);

    void checkIfUserStillLogin(List<User> listUser);

    UserLevelResponseDto mapToUserLevelDto(UserLevel userLevel);
}

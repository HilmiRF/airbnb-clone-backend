package com.hilmirafiff.airbnb_clone_be.service.impl;

import com.hilmirafiff.airbnb_clone_be.dto.OutputSchemaDataResponseDto;
import com.hilmirafiff.airbnb_clone_be.dto.request.userlevel.UpsertUserLevelRequestDto;
import com.hilmirafiff.airbnb_clone_be.dto.response.userlevel.GetAllUserLevelDto;
import com.hilmirafiff.airbnb_clone_be.dto.response.userlevel.GetUserLevelByIdResponseDto;
import com.hilmirafiff.airbnb_clone_be.dto.response.userlevel.UserLevelResponseDto;
import com.hilmirafiff.airbnb_clone_be.entity.User;
import com.hilmirafiff.airbnb_clone_be.util.AppErrorEnum;
import com.hilmirafiff.airbnb_clone_be.util.AppMessageEnum;
import com.hilmirafiff.airbnb_clone_be.entity.UserLevel;
import com.hilmirafiff.airbnb_clone_be.exception.ApplicationWithParamException;
import com.hilmirafiff.airbnb_clone_be.repository.UserLevelRepository;
import com.hilmirafiff.airbnb_clone_be.service.UserLevelService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserLevelServiceImpl implements UserLevelService{

    private final UserLevelRepository userLevelRepo;

    @Override
    public UserLevel getUserLevelById(UUID id) {
        return this.userLevelRepo.findById(id).orElseThrow(() -> new ApplicationWithParamException(AppErrorEnum.RESOURCE_NOT_FOUND, AppMessageEnum.USER_LEVEL.getMessageEn(), null));
    }

    @Override
    public OutputSchemaDataResponseDto<GetUserLevelByIdResponseDto> getUserLevelDtoById(Long id) {
        return null;
    }

    @Override
    public UserLevel getUserLevelDtoById(UUID id) {
        return null;
    }

    @Override
    public OutputSchemaDataResponseDto<List<GetAllUserLevelDto>> getAllUserLevel() {
        return null;
    }

    @Override
    public OutputSchemaDataResponseDto<UserLevelResponseDto> createUserLevel(User user, UpsertUserLevelRequestDto req) {
        return null;
    }

    @Override
    public OutputSchemaDataResponseDto<UserLevelResponseDto> updateUserLevel(User user, Long userLevelId, UpsertUserLevelRequestDto req) {
        return null;
    }

    @Override
    public OutputSchemaDataResponseDto<UserLevelResponseDto> deleteUserLevel(User user, Long userLevelId) {
        return null;
    }

    @Override
    public OutputSchemaDataResponseDto<UserLevelResponseDto> activateUserLevel(User user, Long userLevelId) {
        return null;
    }

    @Override
    public void checkIfUserStillLogin(List<User> listUser) {

    }

    @Override
    public UserLevelResponseDto mapToUserLevelDto(UserLevel userLevel) {
        return null;
    }
}

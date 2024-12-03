package com.hilmirafiff.airbnb_clone_be.service.impl;

import com.hilmirafiff.airbnb_clone_be.dto.request.auth.SignUpRequestDto;
import com.hilmirafiff.airbnb_clone_be.entity.User;
import com.hilmirafiff.airbnb_clone_be.entity.UserLevel;
import com.hilmirafiff.airbnb_clone_be.exception.ApplicationException;
import com.hilmirafiff.airbnb_clone_be.repository.UserLevelRepository;
import com.hilmirafiff.airbnb_clone_be.repository.UserRepository;
import com.hilmirafiff.airbnb_clone_be.service.UserLevelService;
import com.hilmirafiff.airbnb_clone_be.service.UserService;
import com.hilmirafiff.airbnb_clone_be.util.AppErrorEnum;
import com.hilmirafiff.airbnb_clone_be.util.UserLevelLayerEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
//    private final UserLevelRepository userLevelRepository;

    public UserServiceImpl(UserRepository userRepository
//                           UserLevelRepository userLevelRepository
    ) {
        super();
        this.userRepository = userRepository;
//        this.userLevelRepository = userLevelRepository;
    }

    @Override
    public User getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Override
    public User save(SignUpRequestDto signUpRequestDto){
        // UserLevel userLevel = userLevelRepository.findById(signUpRequestDto.getUserLevel()).orElseThrow(() -> new ApplicationException(AppErrorEnum.INTERNAL_SERVER_ERROR));
        User user = new User();
        user.setUserId(UUID.randomUUID());
        user.setEmail(signUpRequestDto.getEmail());
        user.setUsername(signUpRequestDto.getUsername());
        user.setPassword(signUpRequestDto.getPassword());
        user.setCreatedAt(LocalDateTime.now());
        log.info(String.valueOf(user));
        return userRepository.save(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new ApplicationException(AppErrorEnum.USER_NOT_FOUND);
        }
        return (UserDetails) user;
    }

}


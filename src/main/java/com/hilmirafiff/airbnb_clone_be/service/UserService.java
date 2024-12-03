package com.hilmirafiff.airbnb_clone_be.service;

import com.hilmirafiff.airbnb_clone_be.dto.request.auth.SignUpRequestDto;
import com.hilmirafiff.airbnb_clone_be.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.UUID;


public interface UserService extends UserDetailsService {

    User getUserByUsername(String username);

    User save(SignUpRequestDto signUpRequestDto);

    User getUserByEmail(String email);
}

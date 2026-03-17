package com.shopflow.user_service.service.impl;

import com.shopflow.common.BusinessException;
import com.shopflow.common.ResourceNotFoundException;
import com.shopflow.user_service.config.UserMapper;
import com.shopflow.user_service.dto.Request.CreateUserRequest;
import com.shopflow.user_service.dto.Response.UserResponse;
import com.shopflow.user_service.model.User;
import com.shopflow.user_service.repository.UserRepository;
import com.shopflow.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional  // override: this method writes
    public UserResponse createUser(CreateUserRequest request) {
        log.info("Creating user: {}", request.getEmail());

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Email already registered", "USR-001");
        }

        User user = userMapper.toEntity(request);
        user.setPassword(request.getPassword()); // BCrypt added Day 3

        User saved = userRepository.save(user);
        log.info("User created: {}", saved.getId());
        return userMapper.toResponse(saved);
    }

    @Override
    public UserResponse getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found: " + id));
        return userMapper.toResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userMapper.toResponseList(userRepository.findAll());
    }


}

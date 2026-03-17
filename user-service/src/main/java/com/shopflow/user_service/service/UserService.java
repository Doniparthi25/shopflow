package com.shopflow.user_service.service;

import com.shopflow.user_service.dto.Request.CreateUserRequest;
import com.shopflow.user_service.dto.Response.UserResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {
    public UserResponse createUser(CreateUserRequest request);

    public UserResponse getUserById(UUID id);

    public List<UserResponse> getAllUsers();
}

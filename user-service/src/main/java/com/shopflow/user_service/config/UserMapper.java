package com.shopflow.user_service.config;

import com.shopflow.user_service.dto.Request.CreateUserRequest;
import com.shopflow.user_service.dto.Response.UserResponse;
import com.shopflow.user_service.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toResponse(User user);

    @Mapping(target = "password", ignore = true)  // service sets password after BCrypt
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toEntity(CreateUserRequest request);

    List<UserResponse> toResponseList(List<User> users);
}

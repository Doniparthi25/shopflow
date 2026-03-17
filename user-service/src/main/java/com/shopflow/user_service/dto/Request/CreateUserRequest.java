package com.shopflow.user_service.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Must be a valid email")
    private String email;

    @NotBlank @Size(min = 8, message = "Password must be 8+ chars")
    private String password;
    
    @NotBlank @Size(max = 100)
    private String firstName;


    @NotBlank @Size(max = 100)
    private String lastName;
}

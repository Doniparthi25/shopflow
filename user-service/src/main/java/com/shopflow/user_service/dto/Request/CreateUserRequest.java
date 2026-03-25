package com.shopflow.user_service.dto.Request;

import com.shopflow.user_service.validation.StrongPassword;
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

    @NotBlank
    @StrongPassword
    private String password;
    
    @NotBlank @Size(max = 100)
    private String firstName;


    @NotBlank @Size(max = 100)
    private String lastName;
}

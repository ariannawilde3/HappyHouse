package com.happyhouse.dto;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
public class UpdateProfileRequest {
    
    @NotBlank(message = "Name is Required")
    private String name;

    @Email (message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;

    private String currentPassword;
    private String newPassword;
}
